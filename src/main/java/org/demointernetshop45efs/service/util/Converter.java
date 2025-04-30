package org.demointernetshop45efs.service.util;

import org.demointernetshop45efs.dto.UserRequestDto;
import org.demointernetshop45efs.dto.UserResponseDto;
import org.demointernetshop45efs.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Converter {

    public User fromDto(UserRequestDto request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .hashPassword(request.getHashPassword())
                .build();
    }

    public UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();

    }

    public List<UserResponseDto> fromUsers(List<User> users){
        return users.stream()
                .map(user -> toDto(user))
                .toList();
    }
}
