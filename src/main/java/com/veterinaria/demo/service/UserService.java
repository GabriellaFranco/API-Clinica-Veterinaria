package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.UserProfile;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.user.CreateUserDTO;
import com.veterinaria.demo.model.dto.user.GetUserDTO;
import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.model.mapper.UserMapper;
import com.veterinaria.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<GetUserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toGetUserDTO).toList();
    }

    public GetUserDTO getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toGetUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    @Transactional
    public GetUserDTO createUser(CreateUserDTO userDTO) {
        var userMapped = userMapper.toUser(userDTO);

        userMapped.setPassword(encoder.encode(userMapped.getPassword()));
        userMapped.setProfile(defineUserProfile(userMapped));

        var userSaved = userRepository.save(userMapped);
        return userMapper.toGetUserDTO(userSaved);
    }

    private UserProfile defineUserProfile(User user) {
        if (user.getCrmv_number() != null && !user.getCrmv_number().isBlank()) {
            return UserProfile.VETERINARIAN;
        } else {
            return UserProfile.RECEPTION_STAFF;
        }
    }

}
