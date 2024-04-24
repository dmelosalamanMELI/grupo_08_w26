package org.example.social_meli.dto;

import lombok.Data;
import org.example.social_meli.model.User;

import java.util.*;

@Data
public class UserResponseDTO {
    private Integer user_id;
    private String user_name;
    private List<User> follower;
}
