package com.example.wishlistproject.controller;
import com.example.wishlistproject.model.User;
import com.example.wishlistproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    //  GET: Displays the login form
    @GetMapping("/loginModal")
    public String showLoginForm() {
        return "login-modalpage";  // This returns login-modalpage.html
    }

    // POST: Handles the login form submission
    // en session timeout er pr. default 15 minutter

    // LoginModal der popper frem efter man har trykket på log ind.
    @PostMapping("/loginModal")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        // Here, you would validate the user credentials (e.g., check DB)
        User user = userService.loginUser(email, password);

        if (user !=null) {
            session.setAttribute("userId", user.getUserId());
            return "redirect:/list";
        } else {
            // Fejlbesked ved forkert login
            model.addAttribute("error", "Forkert email eller adgangskode");
            return "login-modalpage";
        }
    }

    // Opret Modal, der popper frem efter man har trykket på log ind.
    @GetMapping("/opretModal")
    public String showOpretForm() {
        return "opret-modalpage";  // This returns login-modalpage.html
    }


    // En session timeout er pr. default 15 minutter

    // Login Modal der popper frem efter man har trykket på log ind.
    @PostMapping("/opretModal")
    public String processOpret(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        // Here, you would validate the user credentials (e.g., check DB)
        User user = userService.loginUser(email, password);

        if (user !=null) {
            session.setAttribute("userId", user.getUserId());
            return "redirect:/list";
        } else {
            model.addAttribute("error", "Forkert email eller adgangskode");
            return "opret-modalpage";
        }
    }

    //  GET: Displays the welcome page after successful login
    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";  // This renders the welcome page after login
    }
}
