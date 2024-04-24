package org.example.social_meli.services.impl;

import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.repository.FollowerListRepository;
import org.example.social_meli.repository.IFollowerListRepository;
import org.example.social_meli.services.IFollowerList;
import org.example.social_meli.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FollowerListService implements IFollowerList {
    @Autowired
    private IFollowerListRepository followerListRepository;

    @Override
    public void addFollower(User seller, User follower) {
        try {FollowerList followerList = followerListRepository.findByUser(seller);
            if (followerList == null) {
                followerList = new FollowerList(seller);
            }

            if (!followerList.getFollower().contains(follower)) {
                followerList.getFollower().add(follower);
                followerListRepository.save(followerList);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while saving follower list", e);
        }
    }
}

