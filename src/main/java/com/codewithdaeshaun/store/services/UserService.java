package com.codewithdaeshaun.store.services;

import com.codewithdaeshaun.store.dtos.ChangePasswordRequest;
import com.codewithdaeshaun.store.dtos.RegisterUserRequest;
import com.codewithdaeshaun.store.dtos.UserDto;
import com.codewithdaeshaun.store.entities.User;
import com.codewithdaeshaun.store.exceptions.EmailAlreadyExistsException;
import com.codewithdaeshaun.store.exceptions.InvalidPasswordException;
import com.codewithdaeshaun.store.exceptions.UserNotFoundException;
import com.codewithdaeshaun.store.mappers.UserMapper;
import com.codewithdaeshaun.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(RegisterUserRequest request) {
        // ✅ NEW: Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        // ✅ NEW: Validate password strength
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // ✅ ENHANCED: Better error message
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        // ✅ NEW: Check if new password is different from old password
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidPasswordException("New password must be different from current password");
        }

        // ✅ NEW: Validate new password strength
        if (request.getNewPassword() == null || request.getNewPassword().length() < 8) {
            throw new InvalidPasswordException("New password must be at least 8 characters");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}