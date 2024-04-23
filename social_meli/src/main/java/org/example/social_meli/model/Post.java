package org.example.social_meli.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Integer post_id;
    private Integer user_id;
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
}
