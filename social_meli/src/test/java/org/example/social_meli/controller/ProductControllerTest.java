package org.example.social_meli.controller;

import org.example.social_meli.dto.FollowListDTO;
import org.example.social_meli.services.IProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    IProductService productService;

    @InjectMocks
    ProductController productController;

    @Test
    public void getOrderedAscSellersPostsFollowedByUserTest() {
        //Arrange
        Integer user_id = 1;
        String orderBy = "date_asc";
        FollowListDTO followListDTO = new FollowListDTO();

        when(productService.getOrderedSellersPostsFollowedByUser(user_id, orderBy))
                .thenReturn(followListDTO);

        //Act
        ResponseEntity<?> response = productController.getOrderedSellersPostsFollowedByUser(user_id, orderBy);

        //Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), followListDTO);

    }

    @Test
    public void getOrderedDescSellersPostsFollowedByUserTest() {
        //Arrange
        Integer user_id = 1;
        String orderBy = "date_desc";
        FollowListDTO followListDTO = new FollowListDTO();

        when(productService.getOrderedSellersPostsFollowedByUser(user_id, orderBy))
                .thenReturn(followListDTO);

        //Act
        ResponseEntity<?> response = productController.getOrderedSellersPostsFollowedByUser(user_id, orderBy);

        //Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), followListDTO);

    }
}