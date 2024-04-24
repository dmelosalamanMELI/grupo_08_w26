package org.example.social_meli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IUserRepository {
    FollowerList findSellerById(Integer id);
    FollowerList findClientById(Integer id);
    Integer getClientIndex(FollowerList client);
    Integer getSellerIndex(FollowerList seller);
    void updateClients(Integer index, FollowerList client);
    void updateSellers(Integer index, FollowerList seller);
}
