package org.example.social_meli.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.example.social_meli.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public UserResponseDTO unfollowUser(Integer userId, Integer userIdToUnfollow) {
        ObjectMapper mapper = new ObjectMapper();
        FollowerList client = userRepository.findClientById(userId);
        Integer indexClient = userRepository.getClientIndex(client);
        FollowerList seller = userRepository.findSellerById(userIdToUnfollow);
        Integer indexSeller =  userRepository.getSellerIndex(seller);
        List<User> followers = seller.getFollower();
        followers.removeIf(follower -> follower.getUser_id().equals(userId));
        seller.setFollower(followers);
        List<User> followeds = client.getFollower();
        followeds.removeIf(followed -> followed.getUser_id().equals(userIdToUnfollow));
        client.setFollower(followeds);
        userRepository.updateClients(indexClient,client);
        userRepository.updateSellers(indexSeller,seller);
        UserResponseDTO response = new UserResponseDTO();
        response.setUser_id(client.getUser().getUser_id());
        response.setUser_name(client.getUser().getUser_name());
        response.setFollowers(client.getFollower()
                .stream().map(follow->
                        mapper.convertValue(follow, UserDTO.class))
                .toList());
        return response;
    }
}
