package com.example.demo.repo;

import com.example.demo.model.Users;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<Users, Integer>{
   List<Users> findByUserFirstName(String userFirstName);
   Users findByUserEmailIs(String user_email);

   Users findById(int id);
}
