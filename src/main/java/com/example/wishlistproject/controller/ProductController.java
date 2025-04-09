package com.example.wishlistproject.controller;

import com.example.wishlistproject.model.Product;
import com.example.wishlistproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/wishlist/{wishlistId}/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Vis liste over produkter tilhørende en ønskeliste
    @GetMapping
    public String listProducts(@PathVariable Long wishlistId, Model model) {
        List<Product> products = productService.getProductsByWishlistId(wishlistId);
        model.addAttribute("products", products);
        model.addAttribute("wishlistId", wishlistId);
        return "product/list"; // eks. product/list.html
    }

    // Vis ét produkt
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long wishlistId, @PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("wishlistId", wishlistId);
        return "product/view"; // Refers to product/view.html Thymeleaf template
    }

    // Vis formular til nyt produkt
    @GetMapping("/new")
    public String showCreateForm(@PathVariable Long wishlistId, Model model) {
        Product product = new Product();
        product.setWishlistId(wishlistId);
        model.addAttribute("product", product);
        return "product-form"; // Refers to product/form.html Thymeleaf template
    }

    // Gem produkt
    @PostMapping("/new")
    public String createProduct(@PathVariable Long wishlistId, @ModelAttribute Product product) {
        product.setWishlistId(wishlistId); // sikrer kobling
        productService.addProduct(product);
        return "redirect:/wishlist/view/" + wishlistId;


    }

    // Rediger produkt
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long wishlistId, @PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("wishlistId", wishlistId);
        return "product/form"; // Refers to product/form.html Thymeleaf template
    }

    // Opdater produkt
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long wishlistId, @PathVariable Long id, @ModelAttribute Product product) {
        product.setWishlistId(wishlistId);
        productService.updateProduct(id, product);
        return "redirect:/wishlist" + wishlistId + "/product";
    }

    // Slet produkt
    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long wishlistId, @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/wishlist/view/" + wishlistId;
    }
}