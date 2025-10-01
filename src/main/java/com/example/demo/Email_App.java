package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;


@Service
public class Email_App {

    @Autowired
    private database_storage datastore;

    @Autowired
    private Environment myenvironment;

    private static final String GRAPH_API_URL = "https://graph.microsoft.com/v1.0/users/%s/sendMail";

    public int sendEmail(String accessToken, String userId, String messageContent, String receiver , String identifier) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Construct email body
        Map<String, Object> recipient = new HashMap<>();
        Map<String, String> emailAddress = new HashMap<>();

        emailAddress.put("address", receiver);
        recipient.put("emailAddress", emailAddress);

        List<Map<String, Object>> recipients = new ArrayList<>();
        recipients.add(recipient);

        Map<String, Object> body = new HashMap<>();
        body.put("contentType", "HTML");
        body.put("content", messageContent);

        Map<String, Object> message = new HashMap<>();
        message.put("subject", "Disbursement");
        message.put("body", body);
        message.put("toRecipients", recipients);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("message", message);
        requestBody.put("saveToSentItems", true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        // Build request
        Request request = new Request.Builder()
                .url(String.format(GRAPH_API_URL, userId)) // Dynamically insert userId
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .build();

        // Execute request
        Response response = client.newCall(request).execute();
        System.out.println("Response: " + response.code() + " " + response.body().string());

        CompletableFuture.runAsync(() -> {

            Email_storage_model email_object = new Email_storage_model();
            email_object.setMessagedetails(messageContent);
            email_object.setResponsecode(Integer.toString(response.code()));
            email_object.setIdentifier(identifier);
            datastore.store_return_value_email(email_object);

        });

        return response.code();
    }

}
