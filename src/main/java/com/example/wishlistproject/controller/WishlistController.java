package com.example.wishlistproject.controller;
import com.example.wishlistproject.model.User;
import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.service.UserService;
import com.example.wishlistproject.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//hiiiii
@Controller

public class WishlistController {

    @GetMapping("/")
    public String showFrontPage(){
        return "frontPage";
    }

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    // 👇 GET: Displays the form to create a wishlist
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "create-wishlist"; // corresponds to create-wishlist.html
    }

    // 👇 POST: Processes the form for creating a wishlist
    @PostMapping("/create")
    public String createWishlist(@ModelAttribute Wishlist wishlist, Model model) {
        wishlistService.createWishlist(
                wishlist.getName(),
                wishlist.getDescription(),
                1L // User-id should be set correctly (e.g., from session)
        );
        return "redirect:/list"; // Redirect to wishlist view
    }

    @GetMapping("/list")
    public String specifikUserList(HttpSession session, Model model) {
        Object userIdobj = session.getAttribute("userId");
        if (userIdobj == null){
            return "redirect:/login";

        }
        Long userId = Long.valueOf(userIdobj.toString());
        model.addAttribute("wishlists", wishlistService.getWishlistsForUser(userId));
        return "wishlist-list"; // corresponds to wishlist-list.html
    }

    // 👇 GET: Displays the login form
    @GetMapping("/loginModal")
    public String showLoginForm() {
        return "login-modalpage";  // This returns login-modalpage.html
    }

    // 👇 POST: Handles the login form submission
    // en session timeout er pr. default 15 minutter

    //loginModal der popper frem efter man har trykket på log ind.
    @PostMapping("/loginModal")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        // Here, you would validate the user credentials (e.g., check DB)
        User user = userService.loginUser(email, password);

        if (user !=null) {
            session.setAttribute("userId", user.getUserId());
            return "redirect:/list";
        } else {
            model.addAttribute("error", "Forkert email eller adgangskode");
            return "login-modalpage";


        }
    }

    //opretModal der popper frem efter man har trykket på log ind.
    @GetMapping("/opretModal")
    public String showOpretForm() {
        return "opret-modalpage";  // This returns login-modalpage.html
    }

    // 👇 POST: Handles the login form submission
    // en session timeout er pr. default 15 minutter

    //loginModal der popper frem efter man har trykket på log ind.
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

    // 👇 GET: Displays the welcome page after successful login
    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";  // This renders the welcome page after login
    }
}