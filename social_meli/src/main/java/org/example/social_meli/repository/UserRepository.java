package org.example.social_meli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class UserRepository implements IUserRepository{

    private List<User> userList;
    private List<FollowerList> followerLists;

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();

        userList = new ArrayList<>();
        List<User> users;

        file = ResourceUtils.getFile("classpath:users.json");
        users = objectMapper.readValue(file,new TypeReference<List<User>>(){});
        userList = users;
    }


    @Override
    public int countFollowers(Integer userId) {
        int followerCount = 0;
        for(FollowerList fl : followerLists){
            followerCount = (fl.getUser().getUser_id().equals(userId)) ? fl.getFollower().size() : 0;
        }
        return followerCount;
    }
}
