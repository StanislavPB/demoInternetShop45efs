package org.demointernetshop45efs.service.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailUtil {

    private final Configuration freemakerConfiguration;
    private final JavaMailSender javaMailSender;

    private String createConfirmationMail(String firstName, String lastName, String link) {
        try {
            Template template = freemakerConfiguration.getTemplate("confirm_registration_mail.ftlh");

            Map<String, Object> dataForFillTemplateField = new HashMap<>();
            dataForFillTemplateField.put("firstName", firstName);
            dataForFillTemplateField.put("lastName", lastName);
            dataForFillTemplateField.put("link", link);

            String emailContext = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataForFillTemplateField);

            return emailContext;
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    public void sendEmail(String firstName, String lastName, String link, String subject, String email){

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");

        String textMessage = createConfirmationMail(firstName, lastName, link);

        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(textMessage);
        } catch (MessagingException e){
            throw new IllegalStateException();
        }

        javaMailSender.send(message);
    }

}
