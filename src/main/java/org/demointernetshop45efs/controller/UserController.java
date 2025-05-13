package org.demointernetshop45efs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.controller.api.UserApi;
import org.demointernetshop45efs.dto.ErrorResponseDto;
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
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final UserService service;



    //*найти пользователя по ID

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findUserById(id));
    };

    //*найти пользователя по email
    @GetMapping()
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(service.findUserByEmail(email));
    };


}
