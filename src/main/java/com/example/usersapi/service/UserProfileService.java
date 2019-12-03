package com.example.usersapi.service;

import com.example.usersapi.model.UserProfile;

public interface UserProfileService {
    /**
     * used to construct the profile
     * @param userId Long
     * @param userProfile Object
     * @return user profile
     */
    public UserProfile createProfile(Long userId, UserProfile userProfile);
    public UserProfile getProfile(Long userId);
}
