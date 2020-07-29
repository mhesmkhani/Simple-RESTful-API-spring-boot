package com.module.middleware;

import com.module.model.Users;
import com.module.utils.EncryptionUtils;
import com.module.validation.Validation;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * Created by mhesmkhani on 7/28/2020.
 */
public abstract class UserMiddleware implements Validation, EncryptionUtils{

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

    @Override
    public String UserLoginValidation(Users users) {
        if (users.getEmail().isEmpty() || users.getEmail() == null) {
            return "email is required";
        }else if(users.getPassword().isEmpty() || users.getPassword() == null){
            return "password is required";
        }else{
            return "ok";
        }
    }

    @Override
    public String PasswordEncrypt(Users users) {
        String md5 = DigestUtils.md5Hex(users.getPassword());
        return DigestUtils.shaHex(md5);
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    @Override
    public String TokenGeneration(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return DigestUtils.shaHex(builder.toString());
    }

}
