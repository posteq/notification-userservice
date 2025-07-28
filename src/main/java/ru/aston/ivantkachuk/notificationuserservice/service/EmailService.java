package ru.aston.ivantkachuk.notificationuserservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.aston.ivantkachuk.notificationuserservice.entity.UserEvent;
import ru.aston.ivantkachuk.notificationuserservice.exception.ExceptionSendingEmail;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;


    public void sendUserEventEmail(UserEvent userEvent) {
        try {
            String subject = "";
            String message = "";

            if(userEvent.getOperation().name().equalsIgnoreCase("CREATE")) {
                subject = "Добро пожаловать!";
                message = "Здравствуйте! Ваш аккаунт был успешно создан.";
            }
            if(userEvent.getOperation().name().equalsIgnoreCase("DELETE")) {
                    subject = "Аккаунт удален";
                    message = "Здравствуйте! Ваш аккаунт был удалён.";
            }
            sendMail(userEvent, subject, message);

        } catch (MailException e) {
            log.error("Error when sending email for event: {}", userEvent, e);
            throw new ExceptionSendingEmail("Failed to send email", e);
        }
    }

    private void sendMail(UserEvent userEvent, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(userEvent.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
