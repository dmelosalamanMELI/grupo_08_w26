package org.example.social_meli.repository.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.example.social_meli.model.User;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    private List<User> userList;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        Mockito.reset(userList);
        User user1 = new User(2,"User1",true);
        User user2 = new User(1,"User2",false);
        List <User> users =new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userList.stream()).thenReturn(users.stream());
    }

    @Test
    @DisplayName("Verificar método findByID, usuario existe.")
    void findById_UserExist() {
        Integer userId = 1;
        User found = userRepository.findById(userId);
        Assertions.assertEquals(userId, found.getUser_id(), "El ID del usuario encontrado debe coincidir con el buscado.");
        Assertions.assertNotNull(found, "El usuario encontrado no debe ser nulo.");
    }
    @Test
    @DisplayName("Verificar método findByID, usuario no existe.")
    void findById_UserNoExist() {
        Integer userId = 5;
        User found = userRepository.findById(userId);
        Assertions.assertNull(found, "Debe devolver null si el usuario no existe.");
    }
    @Test
    @DisplayName("Veficar método exixtByID, usuario existente.")
    void existById_UserExits(){
        Integer userId = 1;
        Boolean exists = userRepository.existsById(userId);
        Assertions.assertTrue(exists, "El método debería retornar verdadero para un usuario existente");
    }
    @Test
    @DisplayName("Veficar método exixtByID, usuario no existente.")
    void existById_UserNoExits(){
        Integer userId = 5;
        Boolean exists = userRepository.existsById(userId);
        Assertions.assertFalse(exists, "El método debería retornar falso para un usuario existente");
    }
}