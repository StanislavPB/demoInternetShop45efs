package org.demointernetshop45efs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.dto.UserResponseDto;
import org.demointernetshop45efs.service.ConfirmationCodeService;
import org.demointernetshop45efs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/code")
public class CodeConfirmationController {
    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;


    @GetMapping("/renew")
    public boolean codeRenew(@RequestParam String email){
        return userService.renewCode(email);
    };


    @GetMapping("/confirmation")
    public ResponseEntity<UserResponseDto> confirmationEmail(@RequestParam String codeConfirmation){
        return new ResponseEntity<>(userService.confirmationEmail(codeConfirmation), HttpStatus.OK);
    }


}
