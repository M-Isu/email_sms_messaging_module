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

    private String Messagedetails;

    private String Responsecode;

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
