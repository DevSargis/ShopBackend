package com.example.signin.Mappers;

import com.example.signin.Dto.RegisterDto;
import com.example.signin.Dto.UserDto;
import com.example.signin.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(RegisterDto registerDto);
}