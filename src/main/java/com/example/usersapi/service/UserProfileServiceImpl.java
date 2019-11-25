package com.example.usersapi.service;

import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserProfileRepository;
import com.example.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserProfile createProfile(Long userId, UserProfile userProfile) {
        User currentUser = userRepository.findById(userId).get();
        UserProfile existingProfile = currentUser.getUserProfile();
        if (existingProfile != null) {
            existingProfile.setAdditionalEmail(userProfile.getAdditionalEmail());
            existingProfile.setMobile(userProfile.getMobile());
            existingProfile.setAddress(userProfile.getAddress());
            userProfileRepository.save(existingProfile);
            return existingProfile;
        } else {
            UserProfile savedProfile = userProfileRepository.save(userProfile);
            currentUser.setUserProfile(savedProfile);
            userRepository.save(currentUser);
            return savedProfile;
        }

    }

    @Override
    public UserProfile getProfile(Long userId) {
        User currentUser = userRepository.findById(userId).get();
        UserProfile existingProfile = currentUser.getUserProfile();
        if(existingProfile != null) {
            return existingProfile;
        } else {
            return new UserProfile(); //call const method
        }
    }
}
