package com.module.middleware;

import com.module.model.Todos;
import com.module.utils.TodosValidation;
import com.module.utils.UserValidation;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mhesmkhani on 8/24/2020.
 */
public abstract class TodoMiddleware implements TodosValidation {
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
}
