package com.example.demo.Controller;


import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepo userRepo;



    @GetMapping("main")
    public String main(@RequestParam(required = false,defaultValue = "") String name, Model model) {
        Iterable<Users> users;
        if (name != null && !name.isEmpty()) {
            users = userRepo.findByUserFirstName(name);
        }else{
            users = userRepo.findAll();
        }

        model.addAttribute("users", users);
        return "main";
    }


    @PostMapping("/add")
    public String add(@RequestParam String userCity,
                      @RequestParam String email,
                      @RequestParam String name,
                      @RequestParam String sname,
                      @RequestParam String role,
                      Model model){

        Users userrr = new Users();
        if (!name.isEmpty() && !sname.isEmpty() && !email.isEmpty() && !userCity.isEmpty()) {
            userrr.setUserEmail(email);
            userrr.setUserFirstName(name);
            userrr.setUserLastName(sname);
            userrr.setUserCity(userCity);
            userrr.setActive(false);
            if (role.equals("Admin")){userrr.setRoles(Collections.singleton(Role.ADMIN));}else {userrr.setRoles(Collections.singleton(Role.USER));}

            userRepo.save(userrr);
        }
      name = null;
        sname = null;
        email = null;
        userCity = null;

        return "redirect:/main";
    }

   /* @PostMapping("/filter")
    public String filter(@RequestParam String name, Map<String, Object> model){
    Iterable<Users> users;
    if (name != null && !name.isEmpty()) {
        users = userRepo.findByUserFirstName(name);
    }else{
        users = userRepo.findAll();
    }
        model.put("users",users);

        return "main";
    }*/


    @PostMapping("/updatecity")
    public String updateCity(@RequestParam String userCity,@RequestParam String userId,Model model){
        Users user = null;
        if (!userId.isEmpty() && !userCity.isEmpty()) {

            user = userRepo.findById(Integer.parseInt(userId));
            user.setUserCity(userCity);
            userRepo.save(user);
            model.addAttribute("message","User updated!");

        }else {
            model.addAttribute("message", "User isn't updated!");
        }
        model.addAttribute("users",user);
        return "main";
    }
    @PostMapping("/updatename")
    public String updateName(@RequestParam String userName,@RequestParam String userSurName,@RequestParam String userId,Model model){
        Users user = null;
        if (!userId.isEmpty() && !userName.isEmpty() && !userSurName.isEmpty()) {

             user = userRepo.findById(Integer.parseInt(userId));
            user.setUserFirstName(userName);
            user.setUserLastName(userSurName);
            userRepo.save(user);
            model.addAttribute("message","User updated!");

        }else {
            model.addAttribute("message", "User isn't updated!");
        }
        model.addAttribute("users",user);
        return "main";
    }
    @PostMapping("/updateEmail")
    public String updateEmail(@RequestParam String useremail,@RequestParam String userId,Model model){
        Users user = null;
        if (!userId.isEmpty() && !useremail.isEmpty()) {

            user = userRepo.findById(Integer.parseInt(userId));
            user.setUserEmail(useremail);
            userRepo.save(user);
            model.addAttribute("message","User updated!");

        }else {
            model.addAttribute("message", "User isn't updated!");
        }
        model.addAttribute("users",user);
        return "main";
    }
    @PostMapping("/delete")
    public String updateEmail(@RequestParam String userId,Map<String,Object> model){
        if (!userId.isEmpty()) {
            Users user = userRepo.findById(Integer.parseInt(userId));

            userRepo.delete(user);
            model.put("message","User Deleted!");

        }else {
            model.put("message", "User isn't Deleted!");
        }
        return "main";
    }



}
