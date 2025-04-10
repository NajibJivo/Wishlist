package com.example.wishlistproject.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM product");
        jdbcTemplate.update("DELETE FROM wishlist");
        jdbcTemplate.update("DELETE FROM users");

        jdbcTemplate.update("INSERT INTO users (user_id, email, name, password)" +
                " VALUES  (1, 'test@example.com', 'Test', '1234')");
        jdbcTemplate.update("INSERT INTO wishlist (wishlist_id, name, user_id)" +
                " VALUES (1, 'Testliste', 1)");
    }

    @Test
    void testShowAddProductForm() throws Exception {
        mockMvc.perform(get("/wishlist/1/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-form"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testAddProduct() throws Exception {
        mockMvc.perform(post("/wishlist/1/product/new")
                        .param("name", "Test Produkt")
                        .param("description", "Beskrivelse")
                        .param("price", "100.0")
                        .param("wishUrl", "https://link.dk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/view/1"));
    }

    @Test
    void testShowEditForm() throws Exception {
        jdbcTemplate.update("INSERT INTO product (product_id, name, description, price, wishlist_id, wish_url, purchase_url) " +
                "VALUES (10, 'T-shirt', 'God kvalitet', 100.0, 1, 'https://wish.dk', 'https://køb.dk')");

        mockMvc.perform(get("/wishlist/1/product/edit/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-form"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("wishlistId"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        jdbcTemplate.update("INSERT INTO product (product_id, name, description, price, wishlist_id, wish_url, purchase_url) " +
                "VALUES (11, 'Bog', 'Original', 199.0, 1, 'https://wish.dk', 'https://køb.dk')");

        mockMvc.perform(post("/wishlist/1/product/11")
                        .param("productId", "11")
                        .param("wishlistId", "1")
                        .param("name", "Bog Opdateret")
                        .param("description", "Ny beskrivelse")
                        .param("price", "150.0")
                        .param("wishUrl", "https://ny.dk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/1/product"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        jdbcTemplate.update("INSERT INTO product (product_id, name, description, price, wishlist_id, wish_url, purchase_url) " +
                "VALUES (12, 'Delete', 'Fjern mig', 50.0, 1, 'https://wish.dk', 'https://køb.dk')");

        mockMvc.perform(post("/wishlist/1/product/delete/12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/view/1"));
    }

    @Test
    void testViewProductsForWishlist() throws Exception {
        // Opret produkt i DB
        jdbcTemplate.update("INSERT INTO product (product_id, name, description, price, wishlist_id, wish_url, purchase_url) " +
                "VALUES (13, 'View', 'Vis mig', 75.0, 1, 'https://wish.dk', 'https://køb.dk')");

        mockMvc.perform(get("/wishlist/1/product")) // <-- endpoint med wishlistId
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist-content")) // <-- korrekt viewnavn
                .andExpect(model().attributeExists("wishlist")) // <-- vi forventer en samlet wishlist
                .andExpect(model().attribute("wishlist",
                        Matchers.hasProperty("products")))
                .andExpect(model().attribute("wishlist",
                        Matchers.hasProperty("wishlistId")))
                .andExpect(model().attribute("wishlist",
                        Matchers.hasProperty("name"))) // hvis du sætter navn i controlleren
                .andExpect(model().attribute("wishlist",
                        Matchers.hasProperty("description"))); // hvis du sætter beskrivelse
    }

}