package com.example.demo;

import com.microsoft.aad.msal4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.net.MalformedURLException;


import java.util.Collections;
import java.util.concurrent.CompletableFuture;


@Component
public class OutlookAuth {

    @Autowired
    private Environment environment;

    public String getAccessToken() throws MalformedURLException {

        String CLIENT_ID = environment.getProperty("email.CLIENT_ID");
        String TENANT_ID = environment.getProperty("email.TENANT_ID");
        String CLIENT_SECRET = environment.getProperty("email.CLIENT_SECRET");

        String authority = "https://login.microsoftonline.com/" + TENANT_ID;

        ConfidentialClientApplication app = ConfidentialClientApplication.builder(
                        CLIENT_ID,
                        ClientCredentialFactory.createFromSecret(CLIENT_SECRET))
                .authority(authority)
                .build();

        // Use /.default instead of specific permission scope
        ClientCredentialParameters parameters = ClientCredentialParameters.builder(
                        Collections.singleton("https://graph.microsoft.com/.default"))
                .build();

        try {
            CompletableFuture<IAuthenticationResult> future = app.acquireToken(parameters);
            IAuthenticationResult result = future.join();
            return result.accessToken();
        } catch (Exception e) {
            e.printStackTrace(); // Print detailed error messages
            throw new RuntimeException("Failed to get access token", e);
        }
    }
}