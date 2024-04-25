package org.example.social_meli.controller;

import org.example.social_meli.dto.UserCountResponseDTO;
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

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getFollowedUsers(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getFollowedById(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        userService.followUser(userId, userIdToFollow);
        return new ResponseEntity<>("Usuario creado",HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/count/")
    @ResponseBody
    public ResponseEntity<UserCountResponseDTO> countUserFollowers(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.countFollowers(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowers(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getFollowers(userId), HttpStatus.OK);
    }
}
