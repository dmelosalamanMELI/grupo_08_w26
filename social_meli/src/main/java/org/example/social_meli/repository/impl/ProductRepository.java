package org.example.social_meli.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.model.Post;
import org.example.social_meli.repository.IProductRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private List<Post> postList;

    public ProductRepository() throws IOException {
        this.loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts ;
        file= ResourceUtils.getFile("classpath:posts.json");
        posts= objectMapper.readValue(file,new TypeReference<List<Post>>(){});
        postList = posts;
    }

    private void saveDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        file = ResourceUtils.getFile("classpath:posts.json");
        objectMapper.writeValue(file, postList);
    }

    @Override
    public Post savePost(Post post) {
        postList.add(post);
        try {
            saveDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Boolean existsPost(Integer postId) {
        return postList.stream().anyMatch(post -> post.getPost_id().equals(postId));
    }
}
