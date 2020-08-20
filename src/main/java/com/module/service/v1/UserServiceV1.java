package com.module.service.v1;

import com.module.controller.v1.UserController;
import com.module.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by mhesmkhani on 7/29/2020.
 */
@RestController
@RequestMapping(path = "/api/users")
public class UserServiceV1 extends UserController {

    private UserServiceV1 userController;
    @Autowired
    public void setUserController(UserServiceV1 userController) {
        this.userController = userController;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map> register(Users users) throws Exception {
        try {
           return userController.RegisterUser(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map> login(Users users) throws Exception{
        try {
            return userController.LoginUser(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
