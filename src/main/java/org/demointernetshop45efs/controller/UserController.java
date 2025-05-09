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
    private final UserService userService;


    //*  добавить нового пользователя

    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> addNewUser(@RequestBody UserRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registration(request));
    };

    //* найти всех пользователей (полная информация - для ADMIN)
    @GetMapping("/full")
    public ResponseEntity<List<User>> findAllFullDetails(){
        return ResponseEntity.ok(service.findFullDetailUsers());
    };

    //* найти всех пользователей (ограниченная информация - для MANAGER)
    @GetMapping("/manager/all")
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllUsers());
    };

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

    // найти всех по имени
//    @GetMapping("/name")
//    public ResponseEntity<List<UserResponseDto>> findUserByLastName(String lastName){
//        return ResponseEntity.ok(service.)
//    };

    // * обновить данные от имени пользователь (пользователь хочет
    // поменять какие-то данные в своем профиле)
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.updateUser(request));
    };


    //* удаление записи
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Integer id){
        return service.deleteUser(id);
    };


}
