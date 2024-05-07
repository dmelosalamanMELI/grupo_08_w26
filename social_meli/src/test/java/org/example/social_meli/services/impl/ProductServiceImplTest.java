package org.example.social_meli.services.impl;

import org.example.social_meli.dto.FollowListDTO;
import org.example.social_meli.dto.UserDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.repository.IProductRepository;
import org.example.social_meli.repository.IUserRepository;
import org.example.social_meli.services.IProductService;
import org.example.social_meli.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ProductServiceImplTest {

    @Mock
    IProductService iProductService;

    @Mock
    IProductRepository productRepository;

    @Mock
    IUserRepository userRepository;

    @Mock
    IUserService userService;

    @InjectMocks
    ProductServiceImpl productService;

    UserResponseDTO userResponseDTO;
    FollowListDTO followListDTO;

    @BeforeEach
    public void setup(){
        UserDTO userDTO = new UserDTO(1, "");
        userResponseDTO = new UserResponseDTO(
                1, "", List.of(userDTO)
        );
        followListDTO = new FollowListDTO(
                1, Collections.emptyList()
        );
    }

    @Test
    @DisplayName("Verificar que el tipo de ordenamiento por fecha exista (US-0009)")
    public void getOrderedSellersPostsFollowedByUserExistenceExceptionTest(){
        //Arrange
        Integer id = 1;
        String orderBy = "wrong_input";

        //Act
        when(userService.getFollowedById(anyInt())).thenReturn(userResponseDTO);
        when(iProductService.getSellersPostsFollowedByUser(id)).thenReturn(followListDTO);
        when(iProductService.getAllPosts()).thenReturn(List.of());
        
        //Assert
        assertThrows(
                BadRequestException.class,
                () -> productService.getOrderedSellersPostsFollowedByUser(id, orderBy)
        );
    }

    @Test
    @DisplayName("Verificar que el tipo de ordenamiento por fecha exista (US-0009) ASC")
    public void getAscOrderedSellersPostsFollowedByUserExistenceTest(){
        Integer id = 1;
        String orderBy = "date_asc";

        //Act
        when(userService.getFollowedById(anyInt())).thenReturn(userResponseDTO);
        when(iProductService.getSellersPostsFollowedByUser(id)).thenReturn(followListDTO);
        when(iProductService.getAllPosts()).thenReturn(List.of());

        //Assert
        FollowListDTO actual = productService.getOrderedSellersPostsFollowedByUser(id, orderBy);

        assertEquals(followListDTO, actual);
    }

    @Test
    @DisplayName("Verificar que el tipo de ordenamiento por fecha exista (US-0009) DESC")
    public void getDescOrderedSellersPostsFollowedByUserExistenceTest(){
        Integer id = 1;
        String orderBy = "date_desc";

        //Act
        when(userService.getFollowedById(anyInt())).thenReturn(userResponseDTO);
        when(iProductService.getSellersPostsFollowedByUser(id)).thenReturn(followListDTO);
        when(iProductService.getAllPosts()).thenReturn(List.of());

        //Assert
        FollowListDTO actual = productService.getOrderedSellersPostsFollowedByUser(id, orderBy);

        assertEquals(followListDTO, actual);

    }

}

