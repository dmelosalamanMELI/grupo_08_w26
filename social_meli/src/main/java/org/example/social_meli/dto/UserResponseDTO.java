package org.example.social_meli.dto;

import org.example.social_meli.model.User;

import java.util.*;

public class UserResponseDTO {
    private Integer user_id;
    private String user_name;
    private List<User> followers;
}
