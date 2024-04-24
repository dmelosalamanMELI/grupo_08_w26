package org.example.social_meli.repository;

import org.example.social_meli.model.User;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IUserRepository {
    User findById(Integer user_id);
    boolean existsById(Integer user_Id);
}
