package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.UserProfile;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.user.CreateUserDTO;
import com.veterinaria.demo.model.dto.user.GetUserDTO;
import com.veterinaria.demo.model.entity.Authority;
import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.model.mapper.UserMapper;
import com.veterinaria.demo.repository.AuthorityRepository;
import com.veterinaria.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<GetUserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toGetUserDTO).toList();
    }

    public GetUserDTO getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toGetUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public List<GetUserDTO> getUserByName(String name) {
        return userRepository.findByNameIgnoreCase(name).stream().map(userMapper::toGetUserDTO).toList();
    }

    public List<GetUserDTO> getUsersByProfile(String profile) {
        var userProfile = parseUserProfile(profile);
        return userRepository.findByProfile(userProfile).stream().map(userMapper::toGetUserDTO).toList();
    }

    @Transactional
    public GetUserDTO createUser(CreateUserDTO userDTO) {
        var userMapped = userMapper.toUser(userDTO);

        userMapped.setPassword(encoder.encode(userMapped.getPassword()));
        userMapped.setProfile(defineUserProfileAndAuthority(userMapped));

        var userSaved = userRepository.save(userMapped);
        return userMapper.toGetUserDTO(userSaved);
    }


    private UserProfile defineUserProfileAndAuthority(User user) {
        if (user.getCrmv_number() != null && !user.getCrmv_number().isBlank()) {
            var vetAuthority = authorityRepository.findByName("ROLE_VETERINARIAN")
                    .orElseThrow(() -> new ResourceNotFoundException("Authority not found"));
            user.setAuthorities(List.of(vetAuthority));
            return UserProfile.VETERINARIAN;
        } else {
            var staffAuthority = authorityRepository.findByName("ROLE_RECEPTION")
                    .orElseThrow(() -> new ResourceNotFoundException("Authority not found"));
            user.setAuthorities(List.of(staffAuthority));
            return UserProfile.RECEPTION_STAFF;
        }
    }

    private  UserProfile parseUserProfile(String profile) {
        try {
            return UserProfile.valueOf(profile.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Profile not found: " + profile);
        }
    }


}
