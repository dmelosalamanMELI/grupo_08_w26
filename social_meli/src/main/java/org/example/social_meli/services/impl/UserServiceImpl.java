package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.NotFoundException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.example.social_meli.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Override
    public UserResponseDTO getFollowers(Integer userId) {
        if(userRepository.existById(userId)){
            throw new NotFoundException("No existe el usuario "+userId);
        }
        FollowerList followerList = userRepository.getFollowerByUserId(userId);
        if (followerList == null){
            throw new NotFoundException("No tiene seguidores");
        }
        List<UserDTO> followers = followerList.getFollower().stream()
                .map(user -> objectMapper.convertValue(user,UserDTO.class))
                .collect(Collectors.toList());

        return new UserResponseDTO(userId,userRepository.getUsernameById(userId),followers);

    }
}
