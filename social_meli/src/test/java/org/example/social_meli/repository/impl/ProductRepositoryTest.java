package org.example.social_meli.repository.impl;
import org.example.social_meli.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {


    ProductRepository productRepository;

    @BeforeEach
    void setUp() throws IOException {
        productRepository = new ProductRepository();
    }

    @Test
    void getAllPosts() {

        List<Post> response = productRepository.getAllPosts();
        assertEquals(26, response.size());
        assertNotNull(response);

    }
}