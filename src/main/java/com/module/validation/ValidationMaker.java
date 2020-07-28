package com.module.validation;

import com.module.model.Users;
import com.module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mhesmkhani on 7/28/2020.
 */
public abstract class ValidationMaker implements Validation {

    @Override
    public String UserRegisterValidation(Users users) {
        if (users.getEmail().isEmpty() || users.getEmail() == null) {
            return "email is required";
        }else if(users.getEmail().length() < 6){
            return "email format is incorrect";
        }else if(users.getUsername().isEmpty() || users.getUsername() == null){
            return "username is required";
        }else if(users.getPassword().isEmpty() || users.getPassword() == null){
            return "password is required";
        }else if(users.getPassword().length() < 6){
            return "password is weak";
        }else if(!users.getPassword().equals(users.getConfirmPassword())){
            return "passwords are each other does not match";
        }else {
            return "ok";
        }


    }
}
