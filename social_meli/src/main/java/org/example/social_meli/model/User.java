package org.example.social_meli.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Integer user_id;
    private String user_name;
    private Boolean isSeller;
}
