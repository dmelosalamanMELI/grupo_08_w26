package org.example.social_meli.services.impl;

import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.repository.IProductRepository;
import org.example.social_meli.repository.IUserRepository;
import org.example.social_meli.services.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    IProductRepository productRepository;

    @Mock
    IUserRepository userRepository;

    @Mock
    IUserService userService;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    @DisplayName("Verificar que el tipo de ordenamiento por fecha exista (US-0009)")
    public void getOrderedSellersPostsFollowedByUserExistenceExceptionTest(){
        //Arrange
        Integer id = 1;
        String orderBy = "wrong_input";
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFollower(Collections.emptyList());

        //Act
        when(userService.getFollowedById(id)).thenReturn(userResponseDTO);

        //Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> productService.getOrderedSellersPostsFollowedByUser(id, orderBy)
        );
    }

}

