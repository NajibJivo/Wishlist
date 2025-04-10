package com.example.wishlistproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Sql(scripts = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM product");
        jdbcTemplate.update("DELETE FROM wishlist");
        jdbcTemplate.update("DELETE FROM users");

        jdbcTemplate.update("INSERT INTO users (user_id, email, name, password) " +
                "VALUES (1, 'test@example.com', 'Test Bruger', 'secret')");
        jdbcTemplate.update("INSERT INTO wishlist(name, description, image_url, user_id)" +
                " VALUES ('Test Liste', 'Beskrivelse', 'https://billede.dk/test.jpg', 1)");
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/wishlist/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    void testCreateWishlist_shouldRedirectToLoginIfNotLoggedIn() throws Exception {
        mockMvc.perform(post("/wishlist/create")
                        .param("name", "Ny ønskeseddel")
                        .param("description", "Beskrivelse"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testCreateWishlist_shouldSaveIfLoggedIn() throws Exception {
        mockMvc.perform(post("/wishlist/create")
                        .sessionAttr("userId", 1L)
                        .param("name", "Ny ønskeseddel 2")
                        .param("description", "Beskrivelse")
                        .param("imageUrl", "https://billede.dk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/list"));
    }

    @Test
    void testSpecifikUserList() throws Exception {
        mockMvc.perform(get("/wishlist/list")
                        .sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist-list"))
                .andExpect(model().attributeExists("wishlists"));
    }

    @Test
    void testEditWishlistView() throws Exception {
        mockMvc.perform(get("/wishlist/edit/1")
                        .sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    void testViewWishlist() throws Exception {
        mockMvc.perform(get("/wishlist/view/1")
                        .sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist-content"))
                .andExpect(model().attributeExists("wishlist"));
    }
}