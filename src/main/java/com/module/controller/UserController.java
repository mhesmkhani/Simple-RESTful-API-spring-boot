package com.module.controller;

import com.module.model.Users;
import com.module.repository.UserRepository;
import com.module.validation.ValidationMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mhesmkhani on 7/27/2020.
 */
@RestController
@RequestMapping(path = "/api/users")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "username" }),
        @UniqueConstraint(columnNames = { "email" }) })
public class UserController extends ValidationMaker {


    private ValidationMaker validationMaker;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setUserValidation(ValidationMaker validationMaker) {
        this.validationMaker = validationMaker;
    }

    @GetMapping(path = "/get")
    public List<Users> get(){
        return userRepository.findAll();
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map> RegisterUser(Users users) throws Exception {

        Map map = new HashMap();

        try {
             Map res = new HashMap();
             String msg = validationMaker.UserRegisterValidation(users);
                  if(msg == "ok"){
                      Users user = userRepository.findByEmail(users.getEmail());
                       if(user != null){
                          res.put("messsage","The email is Already");
                          return ResponseEntity.status(200).body(res);
                      }

                      userRepository.save(users);
                      res.put("message","success");
                      return ResponseEntity.status(200).body(res);

                  }else {
                      res.put("message",msg);
                      return ResponseEntity.status(200).body(res);
                  }

         } catch (Exception e) {
            e.printStackTrace();
        }
//        map.put("message",users.getConfirm_password());
//        return ResponseEntity.status(400).body(map);
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }
}
