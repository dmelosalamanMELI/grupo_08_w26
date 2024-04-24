package org.example.social_meli.controller;

import org.example.social_meli.services.IProductService;
import org.example.social_meli.services.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;


}
