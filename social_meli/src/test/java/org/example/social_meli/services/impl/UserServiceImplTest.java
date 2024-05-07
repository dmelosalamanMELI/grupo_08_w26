package org.example.social_meli.services.impl;

import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    /*Crear Mock*/
    @Mock
    private IUserRepository userRepository;
    /*Inyectar Mocks*/
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup(){
    }
    @Test
    /*Verificar que el usuario a seguir exista. (US-0001).*/
    @DisplayName("El usuario no es vendedor")
    void usernotVendedor() {
        /*A Instanciar objeto*/
        Integer userIdValid=1;
        Integer userIdToFollow=2;
        User client = new User(userIdValid, "cliente",false);
        User seller = new User(userIdToFollow, "vendedor",false);
        when(userRepository.existsById(userIdValid)).thenReturn(true);
        when(userRepository.existsById(userIdToFollow)).thenReturn(true);
        when(userRepository.findById(userIdValid)).thenReturn(client);
        when(userRepository.findById(userIdToFollow)).thenReturn(seller);
        /*Arrange*/
        assertThrows(
                BadRequestException.class,
                () -> userService.followUser(userIdValid, userIdToFollow),
                "No puede seguirlo, el usuario no es vendedor."
        );
        /*Assertions*/
        verify(userRepository, times(1)).existsById(userIdValid);
        verify(userRepository, times(1)).existsById(userIdToFollow);
        verify(userRepository, times(1)).findById(userIdValid);
        verify(userRepository, times(1)).findById(userIdToFollow);
    }
    @Test
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001).")
    void existUser() {
        /*A Instanciar objeto*/
        Integer userIdValid=1;
        Integer userIdNotValid=2;
        when(userRepository.existsById(userIdValid)).thenReturn(true);
        when(userRepository.existsById(userIdNotValid)).thenReturn(false);
        /*Arrange*/
        Exception exception=assertThrows(BadRequestException.class,()->{
            userService.followUser(userIdValid, userIdNotValid);
        });
        /*Assertions*/
        Assertions.assertEquals("Uno o ambos usuarios no existen", exception.getMessage());
        verify(userRepository, times(1)).existsById(userIdValid);
        verify(userRepository, times(1)).existsById(userIdNotValid);
    }
}