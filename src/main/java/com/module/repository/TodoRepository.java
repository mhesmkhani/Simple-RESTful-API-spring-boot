package com.module.repository;

import com.module.model.Todos;
import com.module.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by mhesmkhani on 8/24/2020.
 */
public interface TodoRepository extends JpaRepository<Todos,String> {

     List<Todos> findByUserId(String user_id);

}
