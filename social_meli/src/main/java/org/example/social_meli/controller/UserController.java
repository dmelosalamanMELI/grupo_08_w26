package org.example.social_meli.controller;

import org.example.social_meli.model.User;
import org.example.social_meli.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getFollowedUsers(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getFollowedById(userId), HttpStatus.OK);
    }
}
