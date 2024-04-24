package org.example.social_meli.controller;

import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.services.IUserService;
import org.example.social_meli.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("{userId}/followes/count/")
    @ResponseBody
    public ResponseEntity<UserCountResponseDTO> countUserFollowers(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.countFollowers(userId), HttpStatus.OK);
    }
}
