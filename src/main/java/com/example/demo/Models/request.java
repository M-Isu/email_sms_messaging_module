package com.example.demo.Models;

public class request {

      private String channel;
      private String service_name;
      private String notification_type;
      private String phone_number;
      private String message_type;
      private String message_details;
      private String email;
      private String identifier;
      private String message_form;

    public String getMessage_form() {
        return message_form;
    }

    public void setMessage_form(String message_form) {
        this.message_form = message_form;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage_details() {
        return message_details;
    }

    public void setMessage_details(String message_details) {
        this.message_details = message_details;
    }

}
