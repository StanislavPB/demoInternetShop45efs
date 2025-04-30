package org.demointernetshop45efs.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.dto.UserRequestDto;
import org.demointernetshop45efs.dto.UserResponseDto;
import org.demointernetshop45efs.dto.UserUpdateRequestDto;
import org.demointernetshop45efs.entity.ConfirmationCode;
import org.demointernetshop45efs.entity.User;
import org.demointernetshop45efs.repository.UserRepository;
import org.demointernetshop45efs.service.exception.AlreadyExistException;
import org.demointernetshop45efs.service.exception.NotFoundException;
import org.demointernetshop45efs.service.util.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Converter converter;
    private final ConfirmationCodeService confirmationCodeService;

    @Transactional
    public UserResponseDto registration(UserRequestDto request){
        if (repository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("User with email: " + request.getEmail() + " is already exist");
        }

        // ------ а вот если такого пользователя еще нет -----

        User newUser = converter.fromDto(request);
        newUser.setRole(User.Role.USER); // по умолчанию даем пользователю роль USER
        newUser.setStatus(User.Status.NOT_CONFIRMED);

        repository.save(newUser);

        // после создания нового пользователя необходимо создать
        // новый код подтверждения для него и отправить ему на почту

        confirmationCodeService.confirmationCodeHandle(newUser);

        return converter.toDto(newUser);

    }

    public List<UserResponseDto> findAllUsers(){
        List<User> users = repository.findAll();
        List<UserResponseDto> responses = converter.fromUsers(users);

        return responses;
    }

    public UserResponseDto findUserById(Integer id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID = " + id + " not found"));

        return converter.toDto(user);
    }

    public List<User> findFullDetailUsers(){
        return repository.findAll();
    }

    public UserResponseDto findUserByEmail(String email){
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));

        return converter.toDto(user);
    }


    @Transactional
    public UserResponseDto confirmationEmail(String code){

        User user = confirmationCodeService.confirmUserByCode(code);

        user.setStatus(User.Status.CONFIRMED);

        repository.save(user);

        return converter.toDto(user);
    }

    public UserResponseDto updateUser(UserUpdateRequestDto updateRequest){

    };


}
