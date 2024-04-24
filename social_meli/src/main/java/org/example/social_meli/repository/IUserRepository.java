package org.example.social_meli.repository;

import org.example.social_meli.model.FollowerList;

import java.util.List;

public interface IUserRepository {
    FollowerList getFollowerByUserId(Integer userId);
    String getUsernameById(Integer userId);

    Boolean existById(Integer userId);

}
