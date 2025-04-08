package com.example.wishlistproject.controller;

import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
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

@Sql(scripts = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        wishlistRepository.deleteAll();
        jdbcTemplate.update("DELETE FROM users");

        // Tilføj testbruger med user_id = 1
        jdbcTemplate.update("INSERT INTO users(user_id, email, name, password) VALUES (?, ?, ?, ?)",
                1L, "test@example.com", "Test Bruger", "test123");
    }

    // ---------- CREATE ----------

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
                .param("description", "Sommerhus 2025"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/list"));


        // Bekræft at ønskelisten blev gemt
        List<Wishlist> wishlists = wishlistRepository.findByUserId(1L);
        assertEquals(1, wishlists.size());
        assertEquals("Ferieønsker", wishlists.get(0).getName());
    }

    @Test
    void createWishlist_shouldSaveImageUrl_whenUserLoggedIn() throws Exception{
        mockMvc.perform(post("/wishlist/create")
                .sessionAttr("userId", 1L)
                .param("name", "Test Ønskeseddel")
                .param("description", "Test beskrivelse")
                .param("imageUrl", "https://billede.dk/test.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/list"));

        // Bekræft at imageUrl blev gemt korrekt
        List<Wishlist> wishlists = wishlistRepository.findByUserId(1l);
        assertEquals(1,wishlists.size());
        assertEquals("https://billede.dk/test.jpg",wishlists.get(0).getImageUrl());
    }

    @Test
    void showCreateForm_shouldCreateWishlistView() throws Exception {
        mockMvc.perform(get("/wishlist/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    // ---------- READ ----------

    @Test
    void showWishlistList_shouldReturnWishlistListView_whenLoggedIn() throws Exception {
        wishlistRepository.save(new Wishlist("Testliste", "Beskrivelse", "http://billede.dk", 1L));

        mockMvc.perform(get("/wishlist/list").sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist-list"))
                .andExpect(model().attributeExists("wishlists"));
    }

    // ---------- UPDATE ----------

    @Test
    void showEditForm_shouldReturnEditWishlistView_whenLoggedIn() throws Exception {
        Wishlist wishlist = new Wishlist("Testnavn", "Testbeskrivelse", "http://billede.dk", 1L);
        wishlistRepository.save(wishlist);
        Long id = wishlistRepository.findByUserId(1L).get(0).getWishlistId();

        mockMvc.perform(get("/wishlist/edit/" + id).sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    void updateWishlist_shouldUpdateAndRedirect_whenLoggedIn() throws Exception {
        Wishlist wishlist = new Wishlist("Gammel navn", "Gammel beskrivelse", "http://gammel.dk", 1L);
        wishlistRepository.save(wishlist);
        Long id = wishlistRepository.findByUserId(1L).get(0).getWishlistId();

        mockMvc.perform(post("/wishlist/update")
                        .sessionAttr("userId", 1L)
                        .param("wishlistId", String.valueOf(id))
                        .param("name", "Ny titel")
                        .param("description", "Ny beskrivelse")
                        .param("imageUrl", "http://nyt.dk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/list"));

        Wishlist updated = wishlistRepository.findById(id);
        assertEquals("Ny titel", updated.getName());
        assertEquals("Ny beskrivelse", updated.getDescription());
        assertEquals("http://nyt.dk", updated.getImageUrl());
    }

    // ---------- DELETE ----------

    @Test
    void deleteWishlist_shouldDeleteAndRedirect_whenLoggedIn() throws Exception {
        Wishlist wishlist = new Wishlist("Slet mig", "Beskrivelse", "http://slet.dk", 1L);
        wishlistRepository.save(wishlist);
        Long id = wishlistRepository.findByUserId(1L).get(0).getWishlistId();

        mockMvc.perform(get("/wishlist/delete/" + id).sessionAttr("userId", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/list"));

        List<Wishlist> remaining = wishlistRepository.findByUserId(1L);
        assertEquals(0, remaining.size());
    }
}