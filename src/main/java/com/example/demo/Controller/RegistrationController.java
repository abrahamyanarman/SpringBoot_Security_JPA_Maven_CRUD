package com.example.demo.Controller;


import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(Users user, Model model){
        model.addAttribute("user",user);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String userFirstName,@RequestParam String userLastName,@RequestParam String userEmail,@RequestParam String userPassword, Map<String,Object> model)
    {

       Users userFromDb = userRepo.findByUserEmailIs(userEmail);
       if (userFromDb != null){
           model.put("message","User exists!");
           return "registration";
       }else{
           Users users = new Users();
           if (!userFirstName.isEmpty() && !userLastName.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty()) {
               users.setUserEmail(userEmail);
               users.setUserFirstName(userFirstName);
               users.setUserLastName(userLastName);
               users.setUserPassword(userPassword);
               users.setActive(true);
               users.setRoles(Collections.singleton(Role.USER));
               userRepo.save(users);

           }
           userFirstName = null;
           userLastName = null;
           userEmail = null;
           userPassword = null;

           return "redirect:/login";
       }

    }

}
