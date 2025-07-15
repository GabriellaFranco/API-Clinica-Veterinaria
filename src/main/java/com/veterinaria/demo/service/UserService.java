package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.UserProfile;
import com.veterinaria.demo.exception.BusinessException;
import com.veterinaria.demo.exception.OperationNotAllowedException;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.user.UserRequestDTO;
import com.veterinaria.demo.model.dto.user.UserResponseDTO;
import com.veterinaria.demo.model.dto.user.UserUpdateDTO;
import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.model.mapper.UserMapper;
import com.veterinaria.demo.repository.AuthorityRepository;
import com.veterinaria.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseDTO).toList();
    }

    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public List<UserResponseDTO> getUsersByFilter(String name, String crmv,UserProfile profile) {
        return userRepository.findByFilter(name, crmv, profile).stream().map(userMapper::toUserResponseDTO).toList();
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        validateEmail(userDTO.email());
        var userMapped = userMapper.toUser(userDTO);
        validateVeterinarian(userMapped);

        userMapped.setPassword(encoder.encode(userMapped.getPassword()));
        userMapped.setProfile(defineUserProfileAndAuthority(userMapped));

        var userSaved = userRepository.save(userMapped);
        return userMapper.toUserResponseDTO(userSaved);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userDTO) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        applyUpdates(user, userDTO);
        var userSaved = userRepository.save(user);
        return userMapper.toUserResponseDTO(userSaved);
    }

    @Transactional
    public void deleteUser(Long id) {
        var userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        userRepository.delete(userToDelete);
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

    private void applyUpdates(User user, UserUpdateDTO userDTO) {
        Optional.ofNullable(userDTO.name())
                .filter(name -> !name.isBlank())
                .ifPresent(user::setName);

        Optional.ofNullable(userDTO.email())
                .filter(email -> !email.isBlank())
                .ifPresent(user::setEmail);
    }

    private void validateEmail(String email) {
        var user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new OperationNotAllowedException("Email already registered: " + email);
        }
    }

    private void validateVeterinarian(User user) {
        if (user.getProfile().equals(UserProfile.VETERINARIAN)) {
            Optional.ofNullable(user.getCrmv_number())
                    .filter(crmv -> !crmv.isBlank())
                    .orElseThrow(() -> new BusinessException("Veterinarians must informe the crmv number"));
        }
    }
}
