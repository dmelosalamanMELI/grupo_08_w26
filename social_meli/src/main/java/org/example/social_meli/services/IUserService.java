package org.example.social_meli.services;

import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.dto.UserResponseDTO;

public interface IUserService {

    UserCountResponseDTO countFollowers(Integer userId);
}
