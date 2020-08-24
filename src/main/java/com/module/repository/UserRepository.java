package com.module.repository;

import com.module.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by mhesmkhani on 7/27/2020.
 */
@Repository
public interface UserRepository extends JpaRepository<Users,String> {
   Users findByEmail(String email);
   Users findByPhone(String phone);
   Users findByCode(String code);
   Users findByToken(String token);
   Users findByUsername(String username);

}
