package com.example.usersapi.service;

import com.example.usersapi.model.UserProfile;

public interface UserProfileService {
    public UserProfile createProfile(Long userId, UserProfile userProfile);
    public UserProfile getProfile(Long userId);
}
