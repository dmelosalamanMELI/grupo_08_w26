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
    @PostMapping("{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> postUnfollowUser(@PathVariable Integer userId,@PathVariable Integer userIdToUnfollow){
        return new ResponseEntity<>(userService.unfollowUser(userId, userIdToUnfollow),HttpStatus.OK);
    }
}
