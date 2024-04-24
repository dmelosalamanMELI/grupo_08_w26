package org.example.social_meli.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
@Repository
public class UserRepository implements IUserRepository {

    private List<User> userList;
    private List<FollowerList> sellerList;
    private List<FollowerList> clientList;

    public UserRepository() throws IOException {
        loadDataBase();
    }


    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        List<User> users ;
        List<FollowerList> sellers;
        List<FollowerList> clients;

        file= ResourceUtils.getFile("classpath:users.json");
        users= objectMapper.readValue(file,new TypeReference<List<User>>(){});
        userList = users;

        file= ResourceUtils.getFile("classpath:clients.json");
        clients= objectMapper.readValue(file,new TypeReference<List<FollowerList>>(){});
        clientList = clients;

        file= ResourceUtils.getFile("classpath:sellers.json");
        sellers= objectMapper.readValue(file,new TypeReference<List<FollowerList>>(){});
        sellerList = sellers;
    }

    @Override
    public int countFollowers(Integer userId) {
        int counter = 0;

        for (FollowerList fl: sellerList){
            if (fl.getUser().getUser_id().equals(userId)){
                counter = fl.getFollower().size();
            }
        }
        return counter;
    }
}
