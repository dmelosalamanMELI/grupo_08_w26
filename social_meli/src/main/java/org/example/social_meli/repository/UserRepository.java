package org.example.social_meli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Repository
public class UserRepository implements IUserRepository{

    private List<User> userList;
    private List<FollowerList> sellerList;
    private List<FollowerList> clientList;
    private List<FollowerList> followerList;

    public UserRepository() throws IOException {
        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users ;
        file= ResourceUtils.getFile("classpath:users.json");
        users= objectMapper.readValue(file,new TypeReference<List<User>>(){});
        userList = users;
        List<FollowerList> followerLists;
        file= ResourceUtils.getFile("classpath:followerList.json");
        followerLists= objectMapper.readValue(file,new TypeReference<List<FollowerList>>(){});
        followerList = followerLists;
    }



    public FollowerList getFollowerByUserId(Integer userId) {
        return followerList.stream().filter(follower -> follower.getUser().getUser_id().equals(userId)).findFirst().orElseThrow();
    }

    @Override
    public String getUsernameById(Integer userId) {
        return userList.stream().filter(user -> user.getUser_id().equals(userId)).findFirst().orElseThrow().getUser_name();
    }

    @Override
    public Boolean existById(Integer userId) {
        return userList.stream().anyMatch(user -> user.getUser_id().equals(userId));
    }
}
