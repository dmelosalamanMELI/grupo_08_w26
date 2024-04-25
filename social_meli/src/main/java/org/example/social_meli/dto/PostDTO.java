package org.example.social_meli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Integer post_id;
    private Integer user_id;
    private String date;
    private ProductDTO product;
    private Integer category;
    private Double price;
}
