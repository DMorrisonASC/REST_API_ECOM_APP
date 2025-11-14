package com.codewithdaeshaun.store.controllers;

import com.codewithdaeshaun.store.dtos.UserDto;
import com.codewithdaeshaun.store.entities.User;
import com.codewithdaeshaun.store.mappers.UserMapper;
import com.codewithdaeshaun.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort ")
            String sort

    ) {

        if (Set.of("name", "email").contains(sort) == false) {
            sort = "name";
        }

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();

        /* Manually create `UserDto` objects */
//        return userRepository.findAll()
//                .stream()
//                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
//                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
