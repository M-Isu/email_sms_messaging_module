package com.example.demo;


import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "smsMessageTable")
public class storage_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String Messagedetails;

    private String Responsecode;

    private LocalDate time = LocalDate.now();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
