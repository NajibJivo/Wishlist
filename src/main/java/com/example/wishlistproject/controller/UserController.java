package com.example.wishlistproject.controller;
import com.example.wishlistproject.model.User;
import com.example.wishlistproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

   @PostMapping("/register")
    public String registerUser(@RequestBody User user){
       boolean success = userService.registerUser(user);
       return success ? "Bruger oprettet" : "Email eksisterer allerede.";
   }

   @GetMapping("/logout")
    public String logout(HttpSession session){
       session.invalidate();
       return "logout";
   }
}
