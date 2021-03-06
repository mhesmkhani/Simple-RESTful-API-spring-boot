package com.module.controller.v2;

import com.github.mfathi91.time.PersianDate;
import com.module.middleware.TodoMiddleware;
import com.module.model.Todos;
import com.module.model.Users;
import com.module.repository.TodoRepository;
import com.module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by mhesmkhani on 8/24/2020.
 */
public class TodoController extends TodoMiddleware {

    private UserRepository userRepository;
    private TodoRepository todoRepository;
    private TodoMiddleware todoMiddleware;

    @Autowired
    public void setTodoMiddleware(TodoMiddleware todoMiddleware) {
        this.todoMiddleware = todoMiddleware;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public ResponseEntity<Map> Store(HttpServletRequest request, Todos todos) throws Exception {
        Map map = new HashMap();
        try {
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
            DateTimeFormatter curentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            Users user = userRepository.findByToken(token);

            if (token == null) {
                res.put("message", "incorrect request");
                return ResponseEntity.status(403).body(res);
            } else if (user != null) {
                String msg = todoMiddleware.TodosValidation(todos);
                if (msg == "ok") {
                    todos.setUserId(user.getId());
                    todos.setTitle(todos.getTitle());
                    todos.setDiscareption(todos.getDiscareption());
                    todos.setIsdone("0");
                    todos.setCreated_at(date.format(PersianDate.now()) + curentTime.format(now));
                    todoRepository.save(todos);
                    res.put("message", "success");
                    return ResponseEntity.status(200).body(res);
                } else {
                    res.put("message", msg);
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


    public ResponseEntity<Map> All(HttpServletRequest request, Todos todos) throws Exception {
        Map map = new HashMap();
        try {

            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            if (token == null) {
                res.put("message", "incorrect request");
                return ResponseEntity.status(403).body(res);
            } else {
                Users user = userRepository.findByToken(token);
                if (user != null) {
                    List<Todos> todo = todoRepository.findByUserId(user.getId());
                    res.put("message", "success");
                    res.put("data", todo);
                    return ResponseEntity.status(200).body(res);
                } else {
                    res.put("message", "token is invalid");
                    return ResponseEntity.status(200).body(res);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }

    public ResponseEntity<Map> Index(HttpServletRequest request, @PathVariable("id") String id) throws Exception {
        Map map = new HashMap();
        try {
            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            if (token == null) {
                res.put("message", "incorrect request");
                return ResponseEntity.status(403).body(res);
            } else {
                Users user = userRepository.findByToken(token);
                if (user != null) {
                    Optional<Todos> todo = todoRepository.findById(id);
                    if (todo.get().getUserId().equals(user.getId())) {
                        res.put("message", "success");
                        res.put("data", todo);
                        return ResponseEntity.status(200).body(res);
                    } else {
                        res.put("message", "doesn't accessibility");
                        return ResponseEntity.status(403).body(res);
                    }

                } else {
                    res.put("message", "token is invalid");
                    return ResponseEntity.status(200).body(res);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }

    public ResponseEntity<Map> Update(HttpServletRequest request, Todos todos, @PathVariable("id") String id) throws Exception {
        Map map = new HashMap();
        try {
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
            DateTimeFormatter curentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Map res = new HashMap();
            String token = request.getHeader("Authorization");
            if (token == null) {
                res.put("message", "incorrect request");
                return ResponseEntity.status(403).body(res);
            } else {
                Users user = userRepository.findByToken(token);
                if (user != null) {
                    Optional<Todos> todo = todoRepository.findById(id);
                    if (todo.get().getUserId().equals(user.getId())) {
                        String msg = todoMiddleware.TodosValidation(todos);
                        if (msg == "ok") {
                            String result = todoMiddleware.putData(todos);
                            switch (result) {
                                case "discareptionEmpty":
                                    todos.setUserId(user.getId());
                                    todos.setIsdone(todo.get().getIsdone());
                                    todos.setCreated_at(todo.get().getCreated_at());
                                    todos.setTitle(todos.getTitle());
                                    todos.setDiscareption(todo.get().getDiscareption());
                                    todos.setUpdated_at(date.format(PersianDate.now()) + curentTime.format(now));
                                    todoRepository.save(todos);
                                    break;
                                default:
                                    todos.setUserId(user.getId());
                                    todos.setIsdone(todo.get().getIsdone());
                                    todos.setCreated_at(todo.get().getCreated_at());
                                    todos.setTitle(todos.getTitle());
                                    todos.setDiscareption(todos.getDiscareption());
                                    todos.setUpdated_at(date.format(PersianDate.now()) + curentTime.format(now));
                                    todoRepository.save(todos);
                            }

                            res.put("message", "success");
                            return ResponseEntity.status(200).body(res);
                        } else {
                            res.put("message", msg);
                            return ResponseEntity.status(403).body(res);
                        }
                    }
                    res.put("message", "doesn't accessibility");
                    return ResponseEntity.status(403).body(res);
                } else {
                    res.put("message", "token is invalid");
                    return ResponseEntity.status(200).body(res);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("message", "The value of one of the fields is empty");
        return ResponseEntity.status(403).body(map);
    }
}
