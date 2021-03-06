package com.module.controller.v1;

        import com.module.model.Users;
        import com.module.repository.UserRepository;
        import com.module.middleware.UserMiddleware;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;

        import javax.servlet.http.HttpServletRequest;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by mhesmkhani on 7/27/2020.
 */

public class UserController extends UserMiddleware {

    private UserMiddleware userMiddleware;
    private UserRepository userRepository;
     Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setUserMiddleware(UserMiddleware userMiddleware) {
        this.userMiddleware = userMiddleware;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public ResponseEntity<Map> RegisterUser(Users users) throws Exception {

        Map map = new HashMap();
        try {
            Map res = new HashMap();
            String msg = userMiddleware.UserRegisterValidation(users);
            if(msg == "ok"){
                Users user = userRepository.findByEmail(users.getEmail());
                if(user != null){
                    res.put("messsage","The email is Already");
                    return ResponseEntity.status(200).body(res);
                }
                users.setPassword(userMiddleware.PasswordEncrypt(users.getPassword()));
                users.setRole("user");
                users.setToken(null);
                userRepository.save(users);
                res.put("message","success");
                return ResponseEntity.status(200).body(res);

            }else {
                LOG.info("Logger Test");
                res.put("message",msg);
                return ResponseEntity.status(403).body(res);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }

    public ResponseEntity<Map> LoginUser(Users users) throws Exception{
        Map map = new HashMap();
        try {
            Map res = new HashMap();
            String msg = userMiddleware.UserLoginValidation(users);
            String HashPass = userMiddleware.PasswordEncrypt(users.getPassword());
            if(msg == "ok"){
                Users user = userRepository.findByEmail(users.getEmail());
                if(user != null){
                    if(HashPass.equals(user.getPassword())){
                        String token = userMiddleware.TokenGeneration(121);
                        user.setToken(token);
                        userRepository.save(user);
//                      can you set session here
                        res.put("data",user);
                        return ResponseEntity.status(200).body(res);
                    }else {
                        res.put("message","the password or email is wrong");
                        return ResponseEntity.status(403).body(res);
                    }
                }else {
                    res.put("message","the password or email is wrong");
                    return ResponseEntity.status(403).body(res);
                }
            }else {
                res.put("message",msg);
                return ResponseEntity.status(200).body(res);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("message", "server error");
        return ResponseEntity.status(403).body(map);
    }
}
