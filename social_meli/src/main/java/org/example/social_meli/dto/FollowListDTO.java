package org.example.social_meli.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.social_meli.model.Post;
import org.example.social_meli.model.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FollowListDTO {
    private Integer user_id;
    private List<PostDTO> post;
}
