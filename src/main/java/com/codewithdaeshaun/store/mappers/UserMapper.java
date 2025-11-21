package com.codewithdaeshaun.store.mappers;

import com.codewithdaeshaun.store.dtos.RegisterUserRequest;
import com.codewithdaeshaun.store.dtos.UpdateUserRequest;
import com.codewithdaeshaun.store.dtos.UserDto;
import com.codewithdaeshaun.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a User entity to a UserDto
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    UserDto toDto(User user);

    /**
     * Maps a RegisterUserRequest to a User entity for database insertion
     */
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toEntity(RegisterUserRequest request);

    /**
     * Updates an existing User entity with partial data from UpdateUserRequest
     */
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.email", target = "email")
    void update(UpdateUserRequest request, @MappingTarget User user);
}