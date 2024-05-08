package org.example.social_meli.services.impl;

import org.example.social_meli.dto.*;
import org.example.social_meli.model.Post;
import org.example.social_meli.model.Product;
import org.example.social_meli.repository.impl.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<Post> allPosts;

    @BeforeEach
    void setUp() {

        allPosts = new ArrayList<>();
        Product product1 = new Product(1, "product A", "Type A", "Brand A", "Blue", "note");
        LocalDate postLocalDate1 = LocalDate.of(2024, 5, 6);
        Post post1 = new Post(1, 2, postLocalDate1, product1, 1, 12000.0);

        Product product2 = new Product(2, "product B", "Type B", "Brand B", "Blue", "note");
        LocalDate postLocalDate2 = LocalDate.of(2024, 5, 7);
        Post post2 = new Post(2, 2, postLocalDate2, product2, 1, 12000.0);

        allPosts.add(post1);
        allPosts.add(post2);
    }

    @DisplayName("Prueba orden ascendente de posts")
    @Test
    public void getSellersPostsFollowedByUserDescTest() {

        // Configuración de usuario seguido
        UserResponseDTO userResponseDTO = new UserResponseDTO(1, "Juan", List.of(new UserDTO(2, "Pedro"), new UserDTO(3, "Andres")));

        when(userService.getFollowedById(1)).thenReturn(userResponseDTO);
        when(productRepository.getAllPosts()).thenReturn(allPosts);

        FollowListDTO result = productService.getOrderedSellersPostsFollowedByUser(1, "date_asc");

        // ASSERTIONS
        assertEquals(2, result.getPost().size());
        assertEquals(1, result.getPost().get(0).getPost_id()); // post1 deberia ser el primero
        assertEquals(2, result.getPost().get(1).getPost_id()); // post2 deberia ser el segundo
    }

    @DisplayName("Prueba orden descendente de posts")
    @Test
    public void getSellersPostsFollowedByUserAscTest() {

        // Configuración de usuario seguido
        UserResponseDTO userResponseDTO = new UserResponseDTO(1, "Juan", List.of(new UserDTO(2, "Pedro"), new UserDTO(3, "Andres")));

        when(userService.getFollowedById(1)).thenReturn(userResponseDTO);
        when(productRepository.getAllPosts()).thenReturn(allPosts);

        FollowListDTO result = productService.getOrderedSellersPostsFollowedByUser(1, "date_desc");

        // ASSERTIONS
        assertEquals(2, result.getPost().size());
        assertEquals(2, result.getPost().get(0).getPost_id()); // post2 deberia ser el primero
        assertEquals(1, result.getPost().get(1).getPost_id()); // post1 deberia ser el segundo
    }

    @DisplayName("Prueba que no traiga posts de mas de dos semanas")
    @Test
    public void getSellersPostsFollowedByUserTwoWeeksTest() {

        Product product3 = new Product(3, "product C", "Type C", "Brand C", "Blue", "note");
        LocalDate twoWeeksAgoPlusOneDay = LocalDate.now().minusWeeks(3);
        Post post3 = new Post(3, 2, twoWeeksAgoPlusOneDay, product3, 1, 12000.0);
        allPosts.add(post3);

        // Configuración de usuario seguido
        UserResponseDTO userResponseDTO = new UserResponseDTO(1, "Juan", List.of(new UserDTO(2, "Pedro"), new UserDTO(3, "Andres")));

        when(userService.getFollowedById(1)).thenReturn(userResponseDTO);
        when(productRepository.getAllPosts()).thenReturn(allPosts);

        FollowListDTO result = productService.getOrderedSellersPostsFollowedByUser(1, "date_desc");

        // ASSERTIONS
        assertEquals(2, result.getPost().size());
        assertNotEquals(3, result.getPost().get(0).getPost_id());
        assertNotEquals(3, result.getPost().get(1).getPost_id());

    }

}
