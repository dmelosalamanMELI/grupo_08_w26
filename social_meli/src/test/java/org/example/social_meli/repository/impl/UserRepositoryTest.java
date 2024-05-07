package org.example.social_meli.repository.impl;

import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    private UserRepository userRepository;
    private FollowerList followerList;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        userRepository = new UserRepository();
        user = new User(1, "user", false);
        followerList = new FollowerList(user);
    }

    @Test
    @DisplayName("Deberia retornar el indice del cliente")
    void getClientIndex() {
        userRepository.saveClient(followerList);
        Integer index = userRepository.getClientIndex(followerList);
        assertEquals(1, index);
    }

    @Test
    @DisplayName("Deberia retornar el indice del vendedor")
    void getSellerIndex() {
        User user = new User(2, "user", true);
        followerList.setUser(user);

        userRepository.saveSeller(followerList);
        Integer index = userRepository.getSellerIndex(followerList);
        assertEquals(0, index);
    }

    @Test
    @DisplayName("Deberia retornar el usuario por id")
    void findSellerById() {
        FollowerList returnedFollowerList = userRepository.findSellerById(2);
        assertTrue(returnedFollowerList.getUser().getUser_id().equals(2));
    }

    @Test
    @DisplayName("Deberia retornar el usuario por id")
    void findClientById() {
        FollowerList returnedFollowerList = userRepository.findClientById(1);
        assertTrue(returnedFollowerList.getUser().getUser_id().equals(1));
    }

    @Test
    @DisplayName("Deberia retornar el cliente por id despues de actualizarlo")
    void updateClients() {
        userRepository.saveClient(followerList);
        userRepository.updateClients(0, followerList);
        assertEquals(followerList, userRepository.findClientById(1));
    }

    @Test
    @DisplayName("Deberia retornar el vendedor por id despues de actualizarlo")
    void updateSellers() {
        userRepository.saveSeller(followerList);
        userRepository.updateSellers(0, followerList);
        assertEquals(followerList, userRepository.findSellerById(1));
    }
}