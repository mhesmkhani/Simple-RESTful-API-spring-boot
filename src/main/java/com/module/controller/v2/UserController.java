package com.module.controller.v2;

import com.github.mfathi91.time.PersianDate;
import com.module.middleware.UserMiddleware;
import com.module.model.Users;
import com.module.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by mhesmkhani on 8/12/2020.
 */
public class UserController extends UserMiddleware {
    final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    Logger LOG = LoggerFactory.getLogger(com.module.controller.v2.UserController.class);

    private UserMiddleware userMiddleware;
    private UserRepository userRepository;
    private HttpSession session;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Autowired
    public void setUserMiddleware(UserMiddleware userMiddleware) {
        this.userMiddleware = userMiddleware;
    }

    public ResponseEntity<Map> EnterPhoneNumber(Users users) throws Exception {
        Map map = new HashMap();
        try {
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
            DateTimeFormatter curentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Map res = new HashMap();
            String msg = userMiddleware.UserPhoneNumberValidation(users);
            if (msg == "ok") {
                Users user = userRepository.findByPhone("+98" + users.getPhone());
                if (user != null) {
                    res.put("messsage", "The Phone is Already");
                    return ResponseEntity.status(403).body(res);
                }

                String VerificationCode = userMiddleware.GenerateVerificationCode();
                users.setCode(VerificationCode);
                users.setCreated_at(date.format(PersianDate.now()) + curentTime.format(now));
                users.setVerified("0");
                users.setPhone("+98" + users.getPhone());
                userRepository.save(users);
                res.put("message", "success");
                res.put("content", "verification code generated");
                timer.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("runnable" + users.getPhone());
                        Users userFind = userRepository.findByPhone(users.getPhone());
                        if (userFind.getCode().equals(VerificationCode)) {
                            System.out.println("deleted");
                            userRepository.deleteById(userFind.getId());
                        } else {
                            System.out.println("do skip");
                        }
                    }
                }, 60, 1, TimeUnit.SECONDS);
                LOG.info(VerificationCode);
                return ResponseEntity.status(200).body(res);

            } else {
                res.put("message", msg);
                return ResponseEntity.status(403).body(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }

    public ResponseEntity<Map> VerificationCode(HttpServletRequest request, Users users) throws Exception {
        Map map = new HashMap();
        try {
            String code = request.getParameter("code");
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
            DateTimeFormatter curentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String token = userMiddleware.TokenGeneration(121);
            Map res = new HashMap();
            Users user = userRepository.findByCode(code);
            if (user != null) {
                if (code.equals(user.getCode())) {
                    res.put("message", "success");
                    user.setVerified("1");
                    user.setUpdated_at(date.format(PersianDate.now()) + curentTime.format(now));
                    user.setRole("user");
                    user.setToken(token);
                    user.setCode(null);
                    userRepository.save(user);
                    return ResponseEntity.status(200).body(res);
                }
            } else {
                res.put("message", "code is invalid");
                return ResponseEntity.status(403).body(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }

    public ResponseEntity<Map> ModifyUser(HttpServletRequest request, Users users) throws Exception {
        Map map = new HashMap();
        try {
            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            Users user = userRepository.findByToken(token);
            if (user != null) {

            } else {
                res.put("message", "token is invalid");
                return ResponseEntity.status(403).body(res);
            }

            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }


    public ResponseEntity<Map> MakePassword(HttpServletRequest request, Users users) throws Exception {
        Map map = new HashMap();
        try {
            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            Users user = userRepository.findByToken(token);
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
            DateTimeFormatter curentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            if (user != null) {
                String msg = userMiddleware.PasswordValidation(users, request);
                if (msg == "ok") {
                    if (user.getPassword() == null || user.getPassword().isEmpty()) {
                        user.setPassword(userMiddleware.PasswordEncrypt(users.getPassword()));
                        user.setUpdated_at(date.format(PersianDate.now()) + curentTime.format(now));
                        userRepository.save(user);
                        res.put("message", "success");
                        return ResponseEntity.status(200).body(res);
                    } else {
                        res.put("message", "don't accessibility ");
                        return ResponseEntity.status(403).body(res);
                    }
                } else {
                    res.put("message", msg);
                    return ResponseEntity.status(403).body(res);
                }
            } else {
                res.put("message", "token is invalid");
                return ResponseEntity.status(403).body(res);
            }
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);

    }

    public ResponseEntity<Map> ModifyPassword(HttpServletRequest request) throws Exception {
        Map map = new HashMap();
        try {
            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            Users user = userRepository.findByToken(token);
            String HashPass = userMiddleware.PasswordEncrypt(request.getParameter("password"));
            String ConfirmNewPassword = userMiddleware.PasswordEncrypt(request.getParameter("confirm_new_password"));
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
            DateTimeFormatter curentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            if (user != null) {
                if (HashPass.equals(user.getPassword())) {
                    String msg = userMiddleware.PasswordModifyValidation(request);
                    if (msg == "ok") {
                        user.setPassword(ConfirmNewPassword);
                        user.setUpdated_at(date.format(PersianDate.now()) + curentTime.format(now));
                        userRepository.save(user);
                        res.put("message", "success");
                        return ResponseEntity.status(200).body(res);
                    } else {
                        res.put("message", msg);
                        return ResponseEntity.status(403).body(res);
                    }
                } else {
                    res.put("message", "current password is incorrect");
                    return ResponseEntity.status(403).body(res);
                }

            } else {
                res.put("message", "token is invalid");
                return ResponseEntity.status(403).body(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }
}
