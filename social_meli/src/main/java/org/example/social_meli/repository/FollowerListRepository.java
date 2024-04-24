package org.example.social_meli.repository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.model.FollowerList;
import org.springframework.stereotype.Repository;
import org.example.social_meli.model.User;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowerListRepository implements IFollowerListRepository {
    private List<FollowerList> followerLists = new ArrayList<>();

    public FollowerListRepository() throws IOException {
        loadDataBase();
    }

    @Override
    public FollowerList findByUser(User user){
        return followerLists.stream().filter(v -> v.getUser().getUser_id().equals(user.getUser_id())).findFirst().orElse(null);
    }

    private void loadDataBase() throws IOException {
        File file;
        file = ResourceUtils.getFile("classpath:followerList.json");
        List<FollowerList> followerLists2;
        //Resource resource = new ClassPathResource("");
        ObjectMapper objectMapper = new ObjectMapper();
        followerLists2 = objectMapper.readValue(file, new TypeReference<List<FollowerList>>(){});

        followerLists = followerLists2;
    }

    @Override
    public void save(FollowerList followerList) {
        FollowerList existing = findByUser(followerList.getUser());
        if(existing == null) {
            followerLists.add(followerList);
        } else {
            existing.setFollower(followerList.getFollower());
        }
        //saveFile(); // Llama a saveFile que puede arrojar IOException
    }
    private void saveFile() throws IOException {
        Resource resource = new ClassPathResource("followerList.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resource.getFile(), followerLists);  // Caution: this line might not work in a packaged jar
    }

}
