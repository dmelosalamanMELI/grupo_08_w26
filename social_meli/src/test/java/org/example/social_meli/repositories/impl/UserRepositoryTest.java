package org.example.social_meli.repositories.impl;

import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.impl.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class UserRepositoryTest {
    private final UserRepository userRepository;

    public UserRepositoryTest() throws IOException {
        userRepository = new UserRepository();
    }

   @Test
   @DisplayName("Trae el vendedor con el id 2")
    void findClientByIdTest(){
        FollowerList expectedResult = FollowerList.builder()
                .user(new User(2,"dclail1",true))
                .follower(List.of(
                        new User( 3, "ceverett2",false),
                        new User(1,"wcalderwood0",false)
                ))
                .build();
        FollowerList result= userRepository.findSellerById(2);

        assertThat(result,samePropertyValuesAs(expectedResult));
   }
   @Test
   @DisplayName("Traer el cliente con el id 3")
   void  findSellerByIdTest(){
        FollowerList expectedResult = FollowerList.builder()
                .user(new User(3,"ceverett2",false))
                .follower(List.of(
                        new User(2,"dclail1",true),
                        new User(5,"msynnott4",true)
                ))
                .build();
        FollowerList result=userRepository.findClientById(3);
       assertThat(result,samePropertyValuesAs(expectedResult));
   }


}
