package com.codewithdaeshaun.store.mappers;

import com.codewithdaeshaun.store.dtos.UserDto;
import com.codewithdaeshaun.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    /*
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    */
    UserDto toDto(User user);
}
