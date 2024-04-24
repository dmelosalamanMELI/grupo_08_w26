package org.example.social_meli.repository;

import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;

public interface IUserRepository {
    User findById(Integer user_id);
    Boolean existsById(Integer user_Id);
    Boolean existsClientById(Integer clientId);
    Boolean existsSellerById(Integer sellerId);
    FollowerList findSellerByUser(User user);
    FollowerList findClientByUser(User user);
    void saveSeller(FollowerList seller);
    void saveClient(FollowerList client);
}
