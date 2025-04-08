package com.example.wishlistproject.controller;
import com.example.wishlistproject.model.User;
import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.service.UserService;
import com.example.wishlistproject.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/wishlist") //Previously called ("/wishlist) now it's editted to ("")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

   @GetMapping("/")
   public String showFrontPage(){
        return "frontPage";
   }

    // GET: Vis formular til at oprette ønskeliste
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "create-wishlist";
    }

    //  POST: Gem ønskeliste
    @PostMapping("/create")
    public String createWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if(userIdObj == null) {
            return "redirect:/login";  // Redirect, hvis ikke logget ind
        }

        Long userId = Long.valueOf(userIdObj.toString());
       wishlist.setUserId(userId); // Sæt bruger-ID på wishlist
        wishlistService.createWishlist(wishlist); // Gem ønskelisten, tager et Wishlist-objekt med imageUrl

        return "redirect:/wishlist/list"; // Redirect til listevisning
    }

    // GET: Vis ønskelister for den loggede bruger
    @GetMapping("/list")
    public String specifikUserList(HttpSession session, Model model) {
        Object userIdobj = session.getAttribute("userId");
        if (userIdobj == null){
            return "redirect:/login";

        }
        Long userId = Long.valueOf(userIdobj.toString());
        model.addAttribute("wishlists", wishlistService.getWishlistsForUser(userId));
        return "wishlist-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteWishlist(@PathVariable Long id, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if(userIdObj == null) {
            return "redirect:/login";
        }

        wishlistService.deleteWishlist(id);
        return "redirect:/wishlist/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
        Object userIdObj = session.getAttribute("userId");
        if(userIdObj == null) {
            return "redirect/:login";
        }

        Wishlist wishlist = wishlistService.getWishlistById(id);
        model.addAttribute("wishlist", wishlist);
        return "edit-wishlist";
    }

    @PostMapping("/update")
    public String updateWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if(userIdObj == null) {
            return "redirect:/login";
        }

        Long userId = Long.valueOf(userIdObj.toString());
        wishlist.setUserId(userId); // sikrer at brugeren ikke kan ændre id manuelt i HTML
        wishlistService.updateWishlist(wishlist.getWishlistId(), wishlist);
        return "redirect:/wishlist/list";
    }
}