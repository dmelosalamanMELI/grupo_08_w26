package org.example.social_meli.services.impl;

import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    /*Crear Mock*/
    @Mock
    private IUserRepository userRepository;
    /*Inyectar Mocks*/
    @InjectMocks
    private UserServiceImpl userService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001).")
    void followUser() {
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