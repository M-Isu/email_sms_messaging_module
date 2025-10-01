package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class database_storage {

    @Autowired
    private repository myrepository;


    @Autowired
    private repository_email myrepository_email;

    public void store_return_value(storage_model object_model){

        myrepository.save(object_model);


}

    public void store_return_value_email(Email_storage_model value){

        myrepository_email.save(value);

    }


}
