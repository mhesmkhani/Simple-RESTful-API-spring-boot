package com.module.middleware;

import com.module.model.Users;
import com.module.utils.CommonUtils;
import com.module.utils.EncryptionUtils;
import com.module.utils.Validation;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by mhesmkhani on 7/28/2020.
 */
public abstract class UserMiddleware implements Validation, EncryptionUtils, CommonUtils {

    @Override
    public String GenerateVerificationCode() {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public String UserPhoneNumberValidation(Users users) {
        if (users.getPhone().isEmpty() || users.getPhone() == null) {
            return "phone number is required";
        } else if (users.getPhone().length() > 10 || users.getPhone().length() < 10) {
            return "invalid phone number";
        }
        return "ok";
    }

    @Override
    public String UserRegisterValidation(Users users) {
        if (users.getEmail().isEmpty() || users.getEmail() == null) {
            return "email is required";
        } else if (users.getEmail().length() < 6) {
            return "email format is incorrect";
        } else if (users.getUsername().isEmpty() || users.getUsername() == null) {
            return "username is required";
        } else if (users.getPassword().isEmpty() || users.getPassword() == null) {
            return "password is required";
        } else if (users.getPassword().length() < 6) {
            return "password is weak";
        } else {
            return "ok";
        }

    }
//    else if(!users.getPassword().equals(users.getConfirmPassword())){
//        return "passwords are each other does not match";

    @Override
    public String UserLoginValidation(Users users) {
        if (users.getEmail().isEmpty() || users.getEmail() == null) {
            return "email is required";
        } else if (users.getPassword().isEmpty() || users.getPassword() == null) {
            return "password is required";
        } else {
            return "ok";
        }
    }

    @Override
    public String PasswordEncrypt(String password) {
        String md5 = DigestUtils.md5Hex(password);
        return DigestUtils.shaHex(md5);
    }
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String TokenGeneration(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return DigestUtils.shaHex(builder.toString());
    }


    @Override
    public String PasswordValidation(Users users, HttpServletRequest request) {
        if (users.getPassword().isEmpty() || users.getPassword() == null) {
            return "password is required";
        } else if (users.getPassword().length() < 6) {
            return "password is weak";
        } else if (!users.getPassword().equals(request.getParameter("confirm_password"))) {
            return "passwords are doesn't same match";
        } else {
           return  "ok";
        }
    }

    @Override
    public String PasswordModifyValidation(HttpServletRequest request) {
        if (request.getParameter("new_password").isEmpty() || request.getParameter("new_password") == null) {
            return "new password is required";
        }else  if (request.getParameter("confirm_new_password").isEmpty() || request.getParameter("confirm_new_password") == null) {
            return "confirm password is required";
        } else if (request.getParameter("new_password").length() < 6) {
            return "new password is weak";
        } else if (!request.getParameter("new_password").equals(request.getParameter("confirm_new_password"))) {
            return "passwords are doesn't same match";
        } else {
            return  "ok";
        }
    }
}
