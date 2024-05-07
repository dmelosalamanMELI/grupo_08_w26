package org.example.social_meli.services.impl;

import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deberia dejar de seguir a un usuario")
    void unfollowUser() {
        Integer userId = 1;
        Integer userIdToUnfollow = -1;
        User client = new User(userId, "cliente", false);
        User seller = new User(userId, "vendedor", true);

        when(userRepository.findSellerById(any())).thenReturn(new FollowerList(seller));
        when(userRepository.findClientById(any())).thenReturn(new FollowerList(client));

        userService.unfollowUser(userId, userIdToUnfollow);

        Mockito.verify(userRepository, times(1)).findClientById(anyInt());
        Mockito.verify(userRepository, times(1)).getClientIndex(any());
        Mockito.verify(userRepository, times(1)).findSellerById(anyInt());
        Mockito.verify(userRepository, times(1)).getSellerIndex(any());
        Mockito.verify(userRepository, times(1)).updateClients(anyInt(), any());
        Mockito.verify(userRepository, times(1)).updateSellers(anyInt(), any());
    }
}