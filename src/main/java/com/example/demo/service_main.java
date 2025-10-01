package com.example.demo;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@Service
public class service_main {

    @Autowired
    private database_storage databaseStorage;

    @Autowired
    private Email_App email_application;

    private static final String SMS_API_URL = "http://65.108.110.234:5663/api/SendSMS";


    public int sendSms(String apiId, String apiPassword, String smsType, String encoding,
                       String senderId, String phoneNumber, String textMessage) throws IOException {

        String dltEntityId = "45350435";
        String dltEntityTemplateId = "23443456";
        String validityPeriod = "1800";
        String uid = "2erx4r44";
        String callbackUrl = "www.ismartghana.com";
        String isScheduled = "false";

        // Construct the URL with query parameters
        String url = String.format(
                "http://157.180.53.137:5665/api/SendSms?" +
                        "api_id=%s&api_password=%s&sms_type=%s&encoding=%s&sender_id=%s&phonenumber=%s&textmessage=%s" +
                        "&dltEntityId=%s&dltEntityTemplateId=%s&ValidityPeriodInSeconds=%s&uid=%s&callback_url=%s&isScheduled=%s",
                URLEncoder.encode(apiId, StandardCharsets.UTF_8),
                URLEncoder.encode(apiPassword, StandardCharsets.UTF_8),
                URLEncoder.encode(smsType, StandardCharsets.UTF_8),
                URLEncoder.encode(encoding, StandardCharsets.UTF_8),
                URLEncoder.encode(senderId, StandardCharsets.UTF_8),
                URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8),
                URLEncoder.encode(textMessage, StandardCharsets.UTF_8),
                URLEncoder.encode(dltEntityId, StandardCharsets.UTF_8),
                URLEncoder.encode(dltEntityTemplateId, StandardCharsets.UTF_8),
                URLEncoder.encode(validityPeriod, StandardCharsets.UTF_8),
                URLEncoder.encode(uid, StandardCharsets.UTF_8),
                URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8),
                URLEncoder.encode(isScheduled, StandardCharsets.UTF_8)
        );

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader("accept", "application/json");

            try (CloseableHttpResponse response = client.execute(get)) {
                int statusCode = response.getCode();
                System.out.println("Response Code: " + statusCode);
                response.getEntity().writeTo(System.out);

                // Store the message details asynchronously
                CompletableFuture.runAsync(() -> {

                    storage_model store = new storage_model();
                    store.setMessagedetails(textMessage);
                    store.setPhoneNumber(phoneNumber);
                    store.setResponsecode(Integer.toString(statusCode));
                    databaseStorage.store_return_value(store);

                });

                return statusCode;
            }
        }
    }


}
