package com.module.service.v2;

import com.module.controller.v2.TodoController;
import com.module.model.Todos;
import com.module.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by mhesmkhani on 8/24/2020.
 */

@RestController
@RequestMapping(path = "/api/v2/todo")
public class TodoServiceV2  extends TodoController{

     private TodoController todoController;

     @Autowired
     public void setTodoController(TodoServiceV2 todoController) {
         this.todoController = todoController;
     }

    @PostMapping(path = "/AddTodo")
    public ResponseEntity<Map> CreateTodo(HttpServletRequest request, Todos todos) throws Exception {
        try {
            return todoController.Store(request,todos);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(path = "/GetTodo")
     public ResponseEntity<Map> GetTodo(HttpServletRequest request, Todos todos) throws Exception{
         try {
             return todoController.Index(request,todos);
         }catch (Exception e){
             e.printStackTrace();
         }
         return null;
     }

    @GetMapping(path = "{id}")
    public ResponseEntity<Map> UpdateTodo(HttpServletRequest request, Todos todos,@PathVariable("id") String id) throws Exception{
        try {
            return todoController.Update(request,todos,id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
