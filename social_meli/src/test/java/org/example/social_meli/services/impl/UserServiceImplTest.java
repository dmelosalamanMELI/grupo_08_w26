package org.example.social_meli.services.impl;

import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.NotFoundException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
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

    User user = new User();
    FollowerList followerList = new FollowerList(user);

    @BeforeEach
    void setUp() {
        user.setUser_id(1);
        user.setUser_name("Test User");
        followerList.getFollower().add(new User());
        followerList.getFollower().add(new User());
    }

    @Test
    @DisplayName("Deberia dejar de seguir a un usuario")
    void unfollowUser() {
        Integer userId = 1;
        Integer userIdToUnfollow = 2;
        User client = new User(userId, "cliente", false);
        User seller = new User(userIdToUnfollow, "vendedor", true);

        when(userRepository.findSellerById(any())).thenReturn(new FollowerList(seller));
        when(userRepository.findClientById(any())).thenReturn(new FollowerList(client));

        UserResponseDTO returned = userService.unfollowUser(userId, userIdToUnfollow);

        Mockito.verify(userRepository, times(1)).findClientById(anyInt());
        Mockito.verify(userRepository, times(1)).getClientIndex(any());
        Mockito.verify(userRepository, times(1)).findSellerById(anyInt());
        Mockito.verify(userRepository, times(1)).getSellerIndex(any());
        Mockito.verify(userRepository, times(1)).updateClients(anyInt(), any());
        Mockito.verify(userRepository, times(1)).updateSellers(anyInt(), any());

        assertEquals(0, returned.getFollower().size());
    }

    @Test
    @DisplayName("Deberia lanzar una excepcion sino existe el vendedor")
    void unfollowUserWhenSellerNotFound() {
        Integer userId = 12531;
        Integer userIdToUnfollow = 2;
        User client = new User(userId, "cliente", false);

        when(userRepository.findSellerById(any())).thenReturn(null);
        when(userRepository.findClientById(any())).thenReturn(new FollowerList(client));

        assertThrows(NotFoundException.class, () -> userService.unfollowUser(userId, userIdToUnfollow));
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
    @Test
    @DisplayName("Deberia lanzar una excepcion sino existe el cliente")
    void unfollowUserWhenClientNotFound() {
        Integer userId = 1;
        Integer userIdToUnfollow = 22123;

        when(userRepository.findClientById(any())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.unfollowUser(userId, userIdToUnfollow));
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