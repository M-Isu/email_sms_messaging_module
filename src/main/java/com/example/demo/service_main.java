package com.example.demo;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class service_main {

    @Autowired
    private database_storage databaseStorage;

    @Autowired
    private Email_App email_application;

    private static final String SMS_API_URL = "http://157.180.53.137:5665/api/SendSms";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public int sendSms(String apiId, String apiPassword, String messageform, String encoding,
                       String senderId, String phoneNumber, String textMessage, String uid,
                       String messageType, String service_name, String notificationtype) throws IOException {

        String dltEntityId = "45350435";
        String dltEntityTemplateId = "23443456";
        String validityPeriod = "1800";
        String callbackUrl = "www.ismartghana.com";
        String isScheduled = "false";
        String sms_type = "P";

        // Create the JSON request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("api_id", apiId);
        requestBody.put("api_password", apiPassword);
        requestBody.put("sms_type", sms_type);
        requestBody.put("encoding", encoding);
        requestBody.put("sender_id", "ISMART");
        requestBody.put("phonenumber", phoneNumber);
        requestBody.put("textmessage", textMessage);
        requestBody.put("validityPeriodInSeconds", Integer.parseInt(validityPeriod));
        requestBody.put("uid", uid);
        requestBody.put("callback_url", callbackUrl);
        requestBody.put("dltEntityId", Integer.parseInt(dltEntityId));
        requestBody.put("dltEntityTemplateId", Integer.parseInt(dltEntityTemplateId));
        requestBody.put("isScheduled", Boolean.parseBoolean(isScheduled));
        requestBody.put("timeZoneId", 0);
        requestBody.put("finalEffectiveDate", "2025-10-22T08:29:16.276Z");
        requestBody.put("finalEffectiveTime", "08:29:16");

        // Convert to JSON string
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(SMS_API_URL);
            post.setHeader("accept", "text/plain");
            post.setHeader("Content-Type", "application/json");

            // Set the JSON body
            StringEntity entity = new StringEntity(jsonBody);
            post.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getCode();
                System.out.println("Response Code: " + statusCode);
                response.getEntity().writeTo(System.out);

                // Store the message details asynchronously
                CompletableFuture.runAsync(() -> {
                    storage_model store = new storage_model();
                    store.setMessagedetails(textMessage);
                    store.setPhoneNumber(phoneNumber);
                    store.setMessageType(messageType);
                    store.setServiceName(service_name);
                    store.setNotificationType(notificationtype);
                    store.setResponsecode(Integer.toString(statusCode));
                    databaseStorage.store_return_value(store);
                });

                return statusCode;
            }
        }
    }
}