package org.demointernetshop45efs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.dto.UserResponseDto;
import org.demointernetshop45efs.service.ConfirmationCodeService;
import org.demointernetshop45efs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodeConfirmationController {
    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    public boolean codeRenew(String email){
        return userService.renewCode(email);
    };


    public ResponseEntity<UserResponseDto> confirmationEmail(String codeConfirmation){
        return new ResponseEntity<>(userService.confirmationEmail(codeConfirmation), HttpStatus.OK);
    }


}
