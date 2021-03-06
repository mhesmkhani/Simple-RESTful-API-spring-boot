package com.module.utils;

import com.module.model.Todos;
import com.module.model.Users;
import org.apache.catalina.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mhesmkhani on 7/28/2020.
 */
public interface UserValidation {

     String UserRegisterValidation(Users users);
     String UserLoginValidation(Users users);

//     V2 services Validations

     String UserPhoneNumberValidation(Users users);
     String UserModifyValidation(Users users);
     String FindByUserAndEmail(Users email, Users username);
     String PasswordValidation(Users users, HttpServletRequest request);
     String PasswordModifyValidation(HttpServletRequest request);



}
