package com.example.demo.Controller;

import com.example.demo.model.Users;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RequestController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/second")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "second";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/accessdenied")
    public String accessdenied(){return "accessdenied";}

    @PostMapping("/login")
    public String login(@RequestParam String username,@RequestParam String password, Map<String, Object> model){

        if (!username.isEmpty() && !password.isEmpty()){

            Users user =  userRepo.findByUserEmailIs(username);
            model.put("role",user.getRoles().toString());
            if (user == null){
                model.put("message","user not found please register!");
                return "redirect:/registration";
            }

            if (password.equals(user.getUserPassword())){
                model.put("message","logged on!");
                return "redirect:/main";
            }else {
                model.put("message","login or password is not correct!");
                return "redirect:/login";

            }
        }else {
            model.put("message","username or password is empty please check");
            return "redirect:/login";
        }
    }

}
