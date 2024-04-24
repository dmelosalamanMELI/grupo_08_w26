package org.example.social_meli.services;

import org.example.social_meli.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {
    List<UserResponseDTO> getFollowers(Integer userId);
}
