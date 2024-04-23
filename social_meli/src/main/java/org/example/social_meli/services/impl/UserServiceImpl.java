package org.example.social_meli.services.impl;

import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.repository.UserRepository;
import org.example.social_meli.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserCountResponseDTO countFollowers(Integer id) {
        return new UserCountResponseDTO(
                userRepository.getUserList().stream().filter(user -> user.getUser_id().
                        equals(id)).findFirst().get().getUser_id(),
                userRepository.getUserList().stream().filter(user -> user.getUser_id().
                        equals(id)).findFirst().get().getUser_name(),
                userRepository.countFollowers(id)
        );
    }
}
