package com.codewithdaeshaun.store.services;

import com.codewithdaeshaun.store.dtos.*;
import com.codewithdaeshaun.store.entities.User;
import com.codewithdaeshaun.store.exceptions.EmailAlreadyExistsException;
import com.codewithdaeshaun.store.exceptions.InvalidPasswordException;
import com.codewithdaeshaun.store.exceptions.UserNotFoundException;
import com.codewithdaeshaun.store.mappers.UserMapper;
import com.codewithdaeshaun.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Iterable<UserDto> getAllUsers(
            String sort) {

        if (Set.of("name", "email").contains(sort) == false) {
            sort = "name";
        }

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return userMapper.toDto(user);
    }

    public UserDto createUser(RegisterUserRequest request) {
        // Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        // Validate password strength
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public BulkUserResponse createUsers(BulkUserRequest request) {
        List<UserDto> successes = new ArrayList<>();
        List<String> failures = new ArrayList<>();

        for (RegisterUserRequest userRequest : request.getUsers()) {
            try {
                UserDto created = createUser(userRequest);
                successes.add(created);
            } catch (Exception e) {
                failures.add("Failed: " + userRequest.getEmail() + " - " + e.getMessage());
            }
        }

        return new BulkUserResponse(successes, failures, request.getBatchId());
    }

    public UserDto updateUser(
            Long oldUserId,
            UpdateUserRequest updateUserRequest) {

        User oldUser = userRepository.findById(oldUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + oldUserId));

        userMapper.update(updateUserRequest, oldUser);
        userRepository.save(oldUser);
        return userMapper.toDto(oldUser);
    }

    public UserDto deleteUser(
            Long deleteId
    )
    {
        User trash = userRepository.findById(deleteId).orElseThrow(
                () -> new UserNotFoundException("User not found with ID: " + deleteId));

        userRepository.delete(trash);
        return userMapper.toDto(trash);
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        //  Error message if old password is wrong
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        // Check if new password is different from old password
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidPasswordException("New password must be different from current password");
        }

        if (request.getNewPassword() == null || request.getNewPassword().length() < 8) {
            throw new InvalidPasswordException("New password must be at least 8 characters");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }



}