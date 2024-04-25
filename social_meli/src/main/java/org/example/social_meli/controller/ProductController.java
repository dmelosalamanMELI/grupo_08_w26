package org.example.social_meli.controller;

import org.example.social_meli.dto.PostDTO;
import org.example.social_meli.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @PostMapping("/post")
    public ResponseEntity<?> postPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(productService.savePost(postDTO), HttpStatus.OK);
    }
}
