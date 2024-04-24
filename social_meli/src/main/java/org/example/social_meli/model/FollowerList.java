package org.example.social_meli.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowerList {
    private User user;
    @JsonProperty("followers")
    private List<User> follower;
    public FollowerList(User user) {
        this.user = user;
        this.follower = new ArrayList<>();
    }


}