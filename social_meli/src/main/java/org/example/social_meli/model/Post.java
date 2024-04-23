package org.example.social_meli.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Integer post_id;
    private Integer user_id;
    private String date;
    private Product product;
    private Integer category;
    private Double price;
}
