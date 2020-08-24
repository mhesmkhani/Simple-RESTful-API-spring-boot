package com.module.service.v2;

import com.module.controller.v2.UserController;
import com.module.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by mhesmkhani on 8/12/2020.
 */
@RestController
@RequestMapping(path = "/api/v2/user")
public class UserServiceV2 extends UserController {

    private UserServiceV2 userController;
    @Autowired
    public void setUserController(UserServiceV2 userController) {
        this.userController = userController;
    }

    @PostMapping(path = "/PhoneNumber")
    public ResponseEntity<Map> PhoneNumber(Users users) throws Exception {
        try {
            return userController.EnterPhoneNumber(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/VerificationUser")
    public ResponseEntity<Map> VerifiCode(HttpServletRequest request, Users users) throws Exception{
        try {
            return userController.VerificationCode(request, users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/UserUpdate")
    public ResponseEntity<Map> UserUpdate(HttpServletRequest request, Users users) throws Exception{
        try {
            return userController.ModifyUser(request,users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/CreatePassword")
    public ResponseEntity<Map> CreatePassword(HttpServletRequest request, Users users) throws Exception{
        try {
            return userController.MakePassword(request,users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/UpdatePassword")
    public ResponseEntity<Map> UpdatePassword(HttpServletRequest request) throws Exception{
        try {
            return userController.ModifyPassword(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(path = "/Logout")
    public ResponseEntity<Map> Logout(HttpServletRequest request) throws Exception {
        try {
            return userController.UserLogout(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/Login")
    public ResponseEntity<Map> Login(Users users) throws Exception{
        try {
            return userController.UserLogin(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
