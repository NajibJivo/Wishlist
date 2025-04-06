package com.example.wishlistproject.controller;

import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** 🧠 Forklaring
 Del	                                 Hvad det gør
 param(...)	                            Simulerer inputfelter fra formularen
 sessionAttr("userId", 1L)	            Simulerer at brugeren er logget ind
 status().is3xxRedirection()	        Forventer redirect (HTTP 302)
 redirectedUrl(...)	                    Tjekker destination for redirect
 wishlistRepository.findByUserId(1L)	Bekræfter at data blev gemt korrekt  **/

@SpringBootTest
@AutoConfigureMockMvc
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
        wishlistRepository.deleteAll();
    }

    @Test
    void createWishlist_shouldRedirectToLogin_whenUserNotLoggedIn() throws Exception {
        mockMvc.perform(post("/wishlist/create")
                .param("name", "Ferieønsker")
                .param("description" ,"Sommerhus  2025"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void createdWishlist_shouldSaveAndRedirect_whenUserLoggedIn() throws Exception {
        mockMvc.perform(post("/wishlist/create")
                .sessionAttr("userId", 1L)
                .param("name", "Ferieønsker")
                .param("description", "Sommerhus 2025")
                .param("imageUrl", "https://example.com/img.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/list"));


        // Bekræft at ønskelisten blev gemt
        List<Wishlist> wishlists = wishlistRepository.findByUserId(1L);
        assertEquals(1, wishlists.size());
        assertEquals("Ferieønsker", wishlists.get(0).getName());
    }



    /* 🔍 Hvad tester den?           Del	Forklaring
    get("/wishlist/create")	         Simulerer, at brugeren går ind på siden for at oprette ønskeliste
    status().isOk()	                 Forventer, at siden loader korrekt (HTTP 200)
    view().name("create-wishlist")	 Bekræfter, at den rigtige HTML vises
 model().attributeExists("wishlist") Sikrer, at et tomt wishlist-objekt sendes til viewet (til Thymeleaf-formularen)
    *  */
    @Test
    void showCreateForm_shouldCreateWishlistView() throws Exception {
        mockMvc.perform(get("/wishlist/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }


}