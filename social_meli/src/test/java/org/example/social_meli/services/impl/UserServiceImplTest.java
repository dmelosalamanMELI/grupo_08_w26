package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserServiceImpl userService;

    User client;
    FollowerList clientFollowedList;
    User seller;
    FollowerList sellerFollowerList;


    @BeforeEach()
    void setUp(){
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
