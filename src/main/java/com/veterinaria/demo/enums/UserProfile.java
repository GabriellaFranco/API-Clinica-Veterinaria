package com.veterinaria.demo.enums;

import com.veterinaria.demo.exception.ResourceNotFoundException;

public enum UserProfile {
    VETERINARIAN,
    RECEPTION_STAFF;

    private  UserProfile parseUserProfile(String profile) {
        try {
            return UserProfile.valueOf(profile.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Profile not found: " + profile);
        }
    }
}
