package org.example.social_meli.controller;

import org.example.social_meli.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("{userId}/followers/list")
    public ResponseEntity<?> getFollowers(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getFollowers(userId), HttpStatus.OK);
    }
}
