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


@Repository
public class UserRepository implements IUserRepository {

    private List<User> userList;
    private List<FollowerList> sellerList;
    private List<FollowerList> clientList;

    public UserRepository() throws IOException {
        loadDataBase();
    }
    @Override
    public User findById(Integer user_id){
        return userList.stream().filter(v->v.getUser_id().equals(user_id)).findFirst().orElse(null);
    }
    @Override
    public FollowerList findSellerByUser(User user){
        return sellerList.stream().filter(v -> v.getUser().getUser_id().equals(user.getUser_id())).findFirst().orElse(null);
    }
    @Override
    public Boolean existsById(Integer user_Id) {
        return userList.stream().anyMatch(user -> user.getUser_id().equals(user_Id));
    }

    @Override
    public Boolean existsClientById(Integer clientId) {
        return clientList.stream().anyMatch(client -> client.getUser().getUser_id().equals(clientId));
    }

    @Override
    public Boolean existsSellerById(Integer sellerId) {
        return sellerList.stream().anyMatch(client -> client.getUser().getUser_id().equals(sellerId));
    }

    @Override
    public FollowerList findClientByUser(User user) {
        return clientList.stream().filter(v -> v.getUser().getUser_id().equals(user.getUser_id())).findFirst().orElse(null);
    }

    @Override
    public void saveSeller(FollowerList seller) {
        Integer index=-1;
        if(existsSellerById(seller.getUser().getUser_id())){
            index=sellerList.indexOf(findSellerByUser(seller.getUser()));
            this.sellerList.set(index,seller);
            return;
        }
        this.sellerList.add(seller);
    }

    @Override
    public void saveClient(FollowerList client) {
        Integer index=-1;
        if(existsClientById(client.getUser().getUser_id())){
            index=clientList.indexOf(findSellerByUser(client.getUser()));
            this.clientList.set(index,client);
            return;
        }
        this.clientList.add(client);
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

    @Override
    public List<FollowerList> getAllSellers() {
        return sellerList;
    }
}
