package org.example.social_meli.services;

import org.example.social_meli.dto.UserResponseDTO;

public interface IUserService {
    UserResponseDTO unfollowUser(Integer userId, Integer userIdToUnfollow);
}
