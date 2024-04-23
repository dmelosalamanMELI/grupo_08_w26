package org.example.social_meli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.social_meli.model.User;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDTO {
    private Integer user_id;
    private String user_name;
    private List<User> followers;
}
