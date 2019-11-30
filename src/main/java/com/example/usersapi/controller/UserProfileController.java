package com.example.usersapi.controller;

import com.example.usersapi.model.UserProfile;
import com.example.usersapi.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PostMapping("/profile")
    public UserProfile createUserProfile(@RequestHeader("userId") Long userId, @RequestBody UserProfile userProfile){
        return userProfileService.createProfile(userId, userProfile);
    }

    @GetMapping("/profile")
    public UserProfile getUserProfile(@RequestHeader("userId") Long userId){
        return userProfileService.getProfile(userId);
    }

}
