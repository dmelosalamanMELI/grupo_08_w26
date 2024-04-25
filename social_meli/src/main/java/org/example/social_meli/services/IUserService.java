package org.example.social_meli.services;

import org.example.social_meli.dto.UserResponseDTO;

import org.example.social_meli.dto.UserCountResponseDTO;

public interface IUserService {
    UserResponseDTO getFollowers(Integer userId);
    void followUser(Integer user_id, Integer user_id_to_follow);


    UserCountResponseDTO countFollowers(Integer userId);

    UserResponseDTO getFollowedById(Integer id);
}
