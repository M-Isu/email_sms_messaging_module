package com.example.demo;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "emailMessageTable")
public class Email_storage_model {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    @Column(columnDefinition = "TEXT")
    private String Messagedetails;

    private String Responsecode;

    private String NotificationType;

    private String DestinationEmail;

    private String ServiceName;

    @Column(columnDefinition = "TEXT")
    private String MessageType;

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }

    public String getDestinationEmail() {
        return DestinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        DestinationEmail = destinationEmail;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    //            email_object.setNotficationType(notificationtype);
//            email_object.setDestinationEmail(email);
//            email_object.setServiceName(servicename);
//            email_object.setMessageType(messagetype);

    private LocalDate time = LocalDate.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMessagedetails() {
        return Messagedetails;
    }

    public void setMessagedetails(String messagedetails) {
        Messagedetails = messagedetails;
    }

    public String getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(String responsecode) {
        Responsecode = responsecode;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
