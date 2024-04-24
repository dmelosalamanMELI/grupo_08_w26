package org.example.social_meli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Repository
public class UserRepository implements IUserRepository{

    private List<User> userList;

    public UserRepository() throws IOException {
        loadDataBase();
    }
    @Override
    public User findById(Integer user_id){
        return userList.stream().filter(v->v.getUser_id().equals(user_id)).findFirst().orElse(null);
    }
    @Override
    public boolean existsById(Integer user_Id) {
        return userList.stream().anyMatch(user -> user.getUser_id().equals(user_Id));
    }
    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users ;
        file= ResourceUtils.getFile("classpath:users.json");
        users= objectMapper.readValue(file,new TypeReference<List<User>>(){});
        userList = users;
    }
}
