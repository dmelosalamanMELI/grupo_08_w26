package org.example.social_meli.repository;

import org.example.social_meli.model.Post;

public interface IProductRepository {

    Post savePost(Post post);
    Boolean existsPost(Integer postId);
}
