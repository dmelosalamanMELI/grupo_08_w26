package org.example.social_meli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
    public FollowerList findSellerById(Integer id) {
        return sellerList.stream()
                .filter(seller ->
                        seller.getUser().getUser_id().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public FollowerList findClientById(Integer id) {
        return clientList.stream()
                .filter(client ->
                        client.getUser().getUser_id().equals(id))
                .findFirst().orElse(null);
    }


    @Override
    public Integer getClientIndex(FollowerList client) {
        return this.clientList.indexOf(client);
    }

    @Override
    public Integer getSellerIndex(FollowerList seller) {
        return this.sellerList.indexOf(seller);
    }

    @Override
    public void updateClients(Integer index, FollowerList client) {
        this.clientList.set(index,client);
    }

    @Override
    public void updateSellers(Integer index, FollowerList seller) {
        this.sellerList.set(index,seller);
    }
}
