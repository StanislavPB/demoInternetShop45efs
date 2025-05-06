package org.demointernetshop45efs.service;

import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.entity.ConfirmationCode;
import org.demointernetshop45efs.entity.User;
import org.demointernetshop45efs.repository.ConfirmationCodeRepository;
import org.demointernetshop45efs.service.exception.NotFoundException;
import org.demointernetshop45efs.service.mail.MailUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    private final MailUtil mailUtil;

    private final int EXPIRATION_PERIOD = 1;

    private final String LINK_PATH = "code=";


    public void confirmationCodeHandle(User user){
        String code = generateCode();
        saveConfirmationCode(code, user);
        sendCodeByEmail(code, user);
    }

    private String generateCode(){

        // universal uniq identifier
        // формат 128 bit
        // xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
        // где каждый символ 'x' - это либо цифра либо символ от a-f
        // 3f29c3b2-9fc2-11ed-a8fc-0242ac120002

        String code = UUID.randomUUID().toString();
        return code;
    }

    private void saveConfirmationCode(String code, User user){
        ConfirmationCode newCode = new ConfirmationCode();
        newCode.setCode(code);
        newCode.setUser(user);
        newCode.setExpireDataTime(LocalDateTime.now().plusDays(EXPIRATION_PERIOD));
        newCode.setConfirmed(false);
        repository.save(newCode);
    }

    public void sendCodeByEmail(String code, User user){
        String linkToSend = LINK_PATH + code;
        // тут будет отправка пользователю письма с кодом подтверждения

        System.out.println(linkToSend);

        mailUtil.sendEmail(
                user.getFirstName(),
                user.getLastName(),
                linkToSend,
                "Code confirmation email",
                user.getEmail()
        );

    }

    public User confirmUserByCode(String code){
        ConfirmationCode confirmationCode = repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Confirmation code : " + code + " not found"));

        User user = confirmationCode.getUser();

        confirmationCode.setConfirmed(true);
        repository.save(confirmationCode);

        return user;
    }


}
