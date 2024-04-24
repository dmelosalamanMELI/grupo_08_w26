package org.example.social_meli.repository;

import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;

import java.io.IOException;

public interface IFollowerListRepository {
    FollowerList findByUser(User user);
    void save(FollowerList followerList);
}
