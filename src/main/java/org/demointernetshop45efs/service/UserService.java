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

        if (updateRequest.getEmail() == null || updateRequest.getEmail().isBlank()){
            throw new IllegalArgumentException("Email must be provided to update user");
        }

        String userEmail = updateRequest.getEmail();

        // найдем пользователя по email
        User userByEmail = repository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User with email: " + userEmail + " not found"));

        // обновляем все доступные поля
        // мы заранее НЕ ЗНАЕМ, а какие именно поля пользователь захочет поменять
        // то есть в JSON (в теде запроса) будут находится ТОЛЬКО те поля (со значением)
        // которые пользователь хочет менять (не обязательно все)
        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isBlank()) {
            userByEmail.setFirstName(updateRequest.getFirstName());
        }

        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isBlank()) {
            userByEmail.setLastName(updateRequest.getLastName());
        }

        if (updateRequest.getHashPassword() != null && !updateRequest.getHashPassword().isBlank()) {
            userByEmail.setHashPassword(updateRequest.getHashPassword());
        }

        // сохраняем (обновляем) пользователя
        repository.save(userByEmail);

        return converter.toDto(userByEmail);
        // или вручную создать UserResponseDto из данных, которые хранятся в userByEmail
    };

    public boolean deleteUser(Integer id){

        // проверим, что такой id существует
        // и если нет - то сразу возвращаем false и ничего даже не пытаемся удалить

        if (!repository.existsById(id)){
            return false;
        }

        // если существует, то
        // вариант 1 - удаляем сразу по id

        repository.deleteById(id);

        // вариант 2 - сперва найдем объект по этому номеру id

//        User userForDelete = repository.findById(id).get();
//
//        repository.delete(userForDelete);

        return true;

    }


    public boolean renewCode(String email){

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));

        confirmationCodeService.confirmationCodeHandle(user);
        return true;
    }


}
