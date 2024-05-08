package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserDTO;

import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.exceptions.NotFoundException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.example.social_meli.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;
    private IUserService iUserService;


    @InjectMocks
    private UserServiceImpl userService;

    User client;
    FollowerList clientFollowedList;
    User seller;
    FollowerList sellerFollowerList;
    User user = new User();
    FollowerList followerList = new FollowerList(user);

    UserResponseDTO userResponseDTO=new UserResponseDTO();
    Integer id;
    String orderBy;
    String user_name;

    @BeforeEach()
    void setUp(){
        user.setUser_id(1);
        user.setUser_name("Test User");
        followerList.getFollower().add(new User());
        followerList.getFollower().add(new User());

        client = new User(1,"username1",false);

        clientFollowedList= new FollowerList(client,List.of(
                new User(2,"darth",true),
                new User(3,"Aseller",true),
                new User(4,"zorth",true),
                new User(12,"Darth Mouth",true)
        ));

        seller= new User(12,"Darth Mouth",true);

        sellerFollowerList=new FollowerList(seller, List.of(
                new User(1,"username1",false),
                new User(5,"Lisa",false),
                new User(3,"Mister X",false )
        ));

        when(userRepository.existsById(anyInt())).thenReturn(true);
        when(userRepository.existsSellerById(anyInt())).thenReturn(true);
        when(userRepository.existsClientById(anyInt())).thenReturn(true);

        when(userRepository.findClientByUser(client)).thenReturn(clientFollowedList);
        when(userRepository.getFollowerByUserId(12)).thenReturn(sellerFollowerList);

        when(userRepository.findById(1)).thenReturn(client);
        when(objectMapper.convertValue(clientFollowedList.getFollower().get(0), UserDTO.class)).thenReturn(new UserDTO(2,"darth"));
        when(objectMapper.convertValue(clientFollowedList.getFollower().get(1), UserDTO.class)).thenReturn(new UserDTO(3,"Aseller"));
        when(objectMapper.convertValue(clientFollowedList.getFollower().get(2), UserDTO.class)).thenReturn(new UserDTO(4,"zorth"));
        when(objectMapper.convertValue(clientFollowedList.getFollower().get(3), UserDTO.class)).thenReturn(new UserDTO(12,"Darth Mouth"));

        when(objectMapper.convertValue(sellerFollowerList.getFollower().get(0), UserDTO.class)).thenReturn(new UserDTO(1,"username1"));
        when(objectMapper.convertValue(sellerFollowerList.getFollower().get(1), UserDTO.class)).thenReturn(new UserDTO(5,"Lisa"));
        when(objectMapper.convertValue(sellerFollowerList.getFollower().get(2), UserDTO.class)).thenReturn(new UserDTO(3,"Mister X"));

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

        verify(userRepository, times(1)).findClientById(anyInt());
        verify(userRepository, times(1)).getClientIndex(any());
        verify(userRepository, times(1)).findSellerById(anyInt());
        verify(userRepository, times(1)).getSellerIndex(any());
        verify(userRepository, times(1)).updateClients(anyInt(), any());
        verify(userRepository, times(1)).updateSellers(anyInt(), any());

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
        assertEquals("Uno o ambos usuarios no existen", exception.getMessage());
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

    @Test
    @DisplayName("Obtener la lista ordenada ascendentemente de seguidores de un usuario")
    void getAscOrderedFollowersById() {
        id=2;
        orderBy="name_asc";
        user_name="user2";
        userResponseDTO.setUser_id(id);
        userResponseDTO.setUser_name(user_name);
        userResponseDTO.setFollower(List.of());
        FollowerList followerList = new FollowerList(new User(2, "user2", true),List.of());

        when(iUserService.getFollowers(anyInt())).thenReturn(userResponseDTO);
        when(userRepository.findSellerById(anyInt())).thenReturn(followerList);
        when(userRepository.existsSellerById(anyInt())).thenReturn(true);

        UserResponseDTO returned = userService.getOrderedFollowersById(id, orderBy);

        assertEquals(userResponseDTO, returned);
    }

    @Test
    @DisplayName("Obtener la lista ordenada descendentemente de seguidores de un usuario")
    void getDescOrderedFollowersById() {
        id=2;
        orderBy="name_desc";
        user_name="user2";
        userResponseDTO.setUser_id(id);
        userResponseDTO.setUser_name(user_name);
        userResponseDTO.setFollower(List.of());
        FollowerList followerList = new FollowerList(new User(2, "user2", true), List.of());

        when(iUserService.getFollowers(anyInt())).thenReturn(userResponseDTO);
        when(userRepository.findSellerById(anyInt())).thenReturn(followerList);
        when(userRepository.existsSellerById(anyInt())).thenReturn(true);

        UserResponseDTO returned = userService.getOrderedFollowersById(id, orderBy);

        assertEquals(userResponseDTO, returned);
    }

    @Test
    @DisplayName("Obtener la lista ordenada ascendentemente de seguidos de un usuario")
    void getAscOrderedFollowedById() {
        id=1;
        orderBy="name_asc";
        user_name="user1";
        userResponseDTO.setUser_id(id);
        userResponseDTO.setUser_name(user_name);
        userResponseDTO.setFollower(List.of());
        FollowerList followerList = new FollowerList(new User(1, "user1", false),List.of());

        when(iUserService.getFollowedById(anyInt())).thenReturn(userResponseDTO);
        when(userRepository.findClientById(anyInt())).thenReturn(followerList);
        when(userRepository.existsClientById(anyInt())).thenReturn(true);

        UserResponseDTO returned = userService.getOrderedFollowedById(id, orderBy);

        assertEquals(userResponseDTO, returned);
    }

    @Test
    @DisplayName("Obtener la lista ordenada descendentemente de seguidos de un usuario")
    void getDescOrderedFollowedById() {
        id=1;
        orderBy="name_desc";
        user_name="user1";
        userResponseDTO.setUser_id(id);
        userResponseDTO.setUser_name(user_name);
        userResponseDTO.setFollower(List.of());
        FollowerList followerList = new FollowerList(new User(1, "user1", false),List.of());

        when(iUserService.getFollowedById(anyInt())).thenReturn(userResponseDTO);
        when(userRepository.findClientById(anyInt())).thenReturn(followerList);
        when(userRepository.existsClientById(anyInt())).thenReturn(true);

        UserResponseDTO returned = userService.getOrderedFollowedById(id, orderBy);

        assertEquals(userResponseDTO, returned);
    }

    @Test
    @DisplayName("Deberia lanzar una excepcion si el orderBy no es valido al consultar seguidores")
    void getExceptionOrderedFollowersById() {
        id=1;
        orderBy="qwerty";
        user_name="user1";
        FollowerList followerList = new FollowerList(new User(1, "user1", false),List.of());

        when(userRepository.findSellerById(anyInt())).thenReturn(followerList);
        when(userRepository.existsSellerById(anyInt())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> userService.getOrderedFollowersById(id, orderBy));
    }

    @Test
    @DisplayName("Deberia lanzar una excepcion si el orderBy no es valido al consultar seguidos por un usuario")
    void getExceptionOrderedFollowedById() {
        id=1;
        orderBy="qwerty";
        user_name="user1";
        FollowerList followerList = new FollowerList(new User(1, "user1", false),List.of());

        when(userRepository.findClientById(anyInt())).thenReturn(followerList);
        when(userRepository.existsClientById(anyInt())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> userService.getOrderedFollowedById(id, orderBy));
    }
    @Test
    @DisplayName("Trae la lista de seguidos ordenados de forma ascendente")
    void getOrderedFollowedListAscTest(){
        String order = "name_asc";
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .user_id(1)
                .user_name("username1")
                .follower(List.of(
                        new UserDTO(3,"Aseller"),
                        new UserDTO(12,"Darth Mouth"),
                        new UserDTO(2,"darth"),
                        new UserDTO(4,"zorth")
                )).build();

        UserResponseDTO result = userService.getOrderedFollowedById(1,order);

        assertThat(result,samePropertyValuesAs(expectedResult));

    }

    @Test
    @DisplayName("Trae la lista de seguidos ordenados de forma descendente")
    void getOrderedFollowedListDescTest(){
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .user_id(1)
                .user_name("username1")
                .follower(List.of(
                        new UserDTO(4,"zorth"),
                        new UserDTO(2,"darth"),
                        new UserDTO(12,"Darth Mouth"),
                        new UserDTO(3,"Aseller")
                )).build();
        String order = "name_desc";
        UserResponseDTO result = userService.getOrderedFollowedById(1,order);
        assertThat(result,samePropertyValuesAs(expectedResult));

    }

    @Test
    @DisplayName("Trae la lista de seguidores ordenados de forma ascendente")
    void getOrderedFollowerListAscTest(){
        String order = "name_asc";
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .user_id(12)
                .user_name("Darth Mouth")
                .follower(List.of(
                        new UserDTO(5,"Lisa"),
                        new UserDTO(3,"Mister X" ),
                        new UserDTO(1,"username1")
                )).build();

        UserResponseDTO result = userService.getOrderedFollowersById(12,order);

        assertThat(result,samePropertyValuesAs(expectedResult));
    }

    @Test
    @DisplayName("Trae la lista de seguidores ordenados de forma descendente")
    void getOrderedFollowerListDescTest(){
        String order = "name_desc";
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .user_id(12)
                .user_name("Darth Mouth")
                .follower(List.of(
                        new UserDTO(1,"username1"),
                        new UserDTO(3,"Mister X" ),
                        new UserDTO(5,"Lisa")
                )).build();

        UserResponseDTO result = userService.getOrderedFollowersById(12,order);

        assertThat(result,samePropertyValuesAs(expectedResult));
    }


}