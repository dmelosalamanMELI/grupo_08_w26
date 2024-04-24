package org.example.social_meli.services;

import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.model.User;

import java.util.List;

public interface IUserService {
    UserResponseDTO getFollowedById(Integer id);
}
