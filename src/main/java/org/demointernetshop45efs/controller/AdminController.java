package org.demointernetshop45efs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.controller.api.AdminApi;
import org.demointernetshop45efs.controller.api.UserApi;
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
@RequestMapping("/api/admin")
public class AdminController implements AdminApi {

    private final UserService service;



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
