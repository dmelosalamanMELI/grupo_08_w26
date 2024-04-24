package org.example.social_meli.services;
import org.example.social_meli.model.User;

public interface IFollowerList {
    void addFollower(User seller, User follower);
}
