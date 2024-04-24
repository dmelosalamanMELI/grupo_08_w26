package org.example.social_meli.repository;

import org.example.social_meli.model.FollowerList;

import java.util.List;

public interface IUserRepository {
    List<FollowerList> getFollowerListByUserId(Integer userId);

}
