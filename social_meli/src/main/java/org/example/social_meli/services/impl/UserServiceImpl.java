package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.model.FollowerList;
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
    public List<UserResponseDTO> getFollowers(Integer userId) {
        ModelMapper modelMapper = new ModelMapper();
        List<FollowerList> followerList = userRepository.getFollowerListByUserId(userId);
        return followerList.stream()
                .map(follower -> modelMapper.map(follower, UserResponseDTO.class))
                .collect(Collectors.toList());
    }
}
