package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.repository.IUserRepository;
import org.example.social_meli.repository.UserRepository;
import org.example.social_meli.services.IUserService;
import org.example.social_meli.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getFollowedById(Integer id) {
        ObjectMapper mapper = new ObjectMapper();
        FollowerList followerList = userRepository.findUserById(id);
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUser_id(followerList.getUser().getUser_id());
        responseDTO.setUser_name(followerList.getUser().getUser_name());
        responseDTO.setFollowers(followerList.getFollower()
                .stream().map(follower->
                        mapper.convertValue(follower, UserDTO.class))
                .toList());
        return responseDTO;
    }
}
