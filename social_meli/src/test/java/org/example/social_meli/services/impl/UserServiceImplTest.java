package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    User user = new User();
    FollowerList followerList = new FollowerList(user);

    @BeforeEach
    void setUp() {
        user.setUser_id(1);
        user.setUser_name("Test User");
        followerList.getFollower().add(new User());
        followerList.getFollower().add(new User());
    }

    @DisplayName("Test Count Followers")
    @Test
    void countFollowersTest() {

        User user = new User();
        user.setUser_id(1);
        user.setUser_name("Test User");

        FollowerList followerList = new FollowerList(user);
        followerList.getFollower().add(new User());
        followerList.getFollower().add(new User());

        when(userRepository.findSellerById(1)).thenReturn(followerList);

        UserCountResponseDTO result = userService.countFollowers(1);

        assertEquals(1, result.getUser_id());
        assertEquals("Test User", result.getUser_name());
        assertEquals(2, result.getFollowers_count());
    }

    @DisplayName("Test Empty Followers List")
    @Test
    void emptyFollowersListTest() {

        User user = new User();
        user.setUser_id(1);
        user.setUser_name("Test User");

        FollowerList followerList = new FollowerList(user);

        when(userRepository.findSellerById(1)).thenReturn(followerList);

        UserCountResponseDTO result = userService.countFollowers(1);

        assertEquals(1, result.getUser_id());
        assertEquals("Test User", result.getUser_name());
        assertTrue(result.getFollowers_count() == 0);
    }
}