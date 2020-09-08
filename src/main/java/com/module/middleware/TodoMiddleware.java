package com.module.middleware;

import com.github.mfathi91.time.PersianDate;
import com.module.model.Todos;
import com.module.repository.TodoRepository;
import com.module.repository.UserRepository;
import com.module.utils.TodosValidation;
import com.module.utils.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mhesmkhani on 8/24/2020.
 */
public abstract class TodoMiddleware implements TodosValidation {
    private TodoRepository todoRepository;

    private UserRepository userRepository;

    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public String TodosValidation(Todos todos) {
      if(todos.getTitle().isEmpty() || todos.getTitle() == null){
          return "title is required";
      }else {
          return "ok";
      }
    }

    public String CheckToken(String token){
        if (token == null) {
            return "incorrect request";
        } else  {
            return "token is invalid";
        }
    }

    public String putData(Todos todos){
        if(todos.getDiscareption().isEmpty()){
           return "discareptionEmpty";
        }else{
            return "ok";
        }
    }
}
