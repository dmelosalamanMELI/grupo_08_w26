package org.example.social_meli.services;

import java.io.IOException;

import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.dto.UserResponseDTO;

public interface IUserService {
    void followUser(Integer user_id, Integer user_id_to_follow);

    UserCountResponseDTO countFollowers(Integer userId);
}
