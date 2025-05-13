package org.demointernetshop45efs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.controller.api.PublicApi;
import org.demointernetshop45efs.controller.api.UserApi;
import org.demointernetshop45efs.dto.UserRequestDto;
import org.demointernetshop45efs.dto.UserResponseDto;
import org.demointernetshop45efs.dto.UserUpdateRequestDto;
import org.demointernetshop45efs.entity.User;
import org.demointernetshop45efs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicController implements PublicApi {

    private final UserService service;


    //*  добавить нового пользователя

    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> addNewUser(@RequestBody UserRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registration(request));
    };


}
