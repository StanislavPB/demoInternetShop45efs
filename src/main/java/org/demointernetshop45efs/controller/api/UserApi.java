package org.demointernetshop45efs.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

@RequestMapping("/api/users")
public interface UserApi {

    //*  добавить нового пользователя

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> addNewUser(@RequestBody UserRequestDto request);


    //* найти всех пользователей (полная информация - для ADMIN)
    @GetMapping("/full")
    public ResponseEntity<List<User>> findAllFullDetails();


    //* найти всех пользователей (ограниченная информация - для MANAGER)
    @GetMapping("/manager/all")
    public ResponseEntity<List<UserResponseDto>> findAll();

    //*найти пользователя по ID
    @Operation(summary = "Получение информации о пользователе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Integer id);

    //*найти пользователя по email
    @GetMapping()
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email);

    // найти всех по имени
//    @GetMapping("/name")
//    public ResponseEntity<List<UserResponseDto>> findUserByLastName(String lastName){
//        return ResponseEntity.ok(service.)
//    };

    // * обновить данные от имени пользователь (пользователь хочет
    // поменять какие-то данные в своем профиле)
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto request);


    //* удаление записи
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Integer id);


}
