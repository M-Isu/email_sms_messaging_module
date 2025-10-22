package com.example.demo;

import java.util.UUID;
import com.example.demo.Models.request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/send")
public class controller_main {


    @Autowired
    private service_main myservice;

    @Autowired
    private OutlookAuth outlookAuthx;

    @Autowired
    private Email_App email_application;

    @Autowired
    private Environment environment;

    @PostMapping("/message")
    public ResponseEntity<?> controller(@RequestBody request value) throws IOException {

        //String apiId, String apiPassword, String smsType, String encoding,
        //String senderId, String phoneNumber, String textMessage


        if(value.getChannel().equals("sms")) {

            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();

            System.out.println(environment.getProperty("sms.apiId"));

            //String messageType, String service_name, String notificationtype

            int result = myservice.sendSms(environment.getProperty("sms.apiId"),
                    environment.getProperty("sms.apiPassword"),
                    value.getMessage_form(),
                    environment.getProperty("sms.encoding"),
                    value.getService_name(),
                    value.getPhone_number(),
                    value.getMessage_details(),
                    uuidAsString,
                    value.getMessage_type(),
                    value.getService_name(),
                    value.getNotification_type()
            );

            if(result == 200){
                Map<String, Object> responseBody = Map.of(
                        "status", "success",
                        "message", "Message sent successfully",
                        "code", 200
                );

                return ResponseEntity.ok(responseBody);
            }
            else{
                Map<String, Object> responseBody = Map.of(
                        "status", "failed",
                        "message", "Message failed",
                        "code", 400
                );

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseBody);
            }


        }
        else if(value.getChannel().equals("email")){
//          String accessToken, String userId, String messageContent, String receiver
            //get access token for email.


            String accessToken = outlookAuthx.getAccessToken();

            int response_code = email_application.sendEmail(accessToken,
                    environment.getProperty("email.senderEmail"),
                    value.getMessage_details(),
                    value.getEmail(),
                    value.getIdentifier(),
                    value.getNotification_type(),
                    value.getService_name(),
                    value.getMessage_type());
            //will have to change this to use an actual email api.

            if(response_code == 202){
                Map<String, Object> responseBody = Map.of(
                        "status", "Success",
                        "message", "Message sent via email",
                        "code", 200
                );
                return ResponseEntity.ok(responseBody);

            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }
        else if(value.getChannel().equals("Application")){


            String accessToken = outlookAuthx.getAccessToken();

            int response_code = email_application.sendEmail(accessToken, environment.getProperty("email.userId"),value.getMessage_details(), value.getEmail(), value.getIdentifier(),
                    value.getNotification_type(),
                    value.getService_name(),
                    value.getMessage_type());

            if(response_code == 202){
                Map<String, Object> responseBody = Map.of(
                        "status", "Success",
                        "message", "Message sent via email to application",
                        "code", 200
                );
                return ResponseEntity.ok(responseBody);

            }
            else{
                return ResponseEntity.badRequest().build();
            }

        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

//    {
//        "channel": "email",
//            "service_name": "auth-service",
//            "notification_type": "password_reset",
//            "email": "user@example.com",
//            "message_type": "text",
//            "message_details": "Hello John, use this link to reset your password: https://example.com/reset?token=xyz123. This link expires in 15 minutes."
//    }


}
