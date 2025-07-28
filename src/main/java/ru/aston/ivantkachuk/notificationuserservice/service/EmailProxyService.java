package ru.aston.ivantkachuk.notificationuserservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.aston.ivantkachuk.notificationuserservice.dto.EmailResponseDto;
import ru.aston.ivantkachuk.notificationuserservice.dto.UserEventRequestDto;
import ru.aston.ivantkachuk.notificationuserservice.entity.UserEvent;

@Service
@RequiredArgsConstructor
public class EmailProxyService {
    private static final Logger logger = LoggerFactory.getLogger(EmailProxyService.class);

    private final EmailService emailService;

    public EmailResponseDto sendEmail(UserEventRequestDto requestEvent){
        logger.info("Processing email request: {}", requestEvent);
        UserEvent event = UserEvent.builder()
                .email(requestEvent.getEmail())
                .operation(requestEvent.getOperation())
                .build();
        emailService.sendUserEventEmail(event);
        logger.info("Email sent successfully: {}", event.getEmail());

        return new EmailResponseDto(event.getEmail(), "Email sent successfully");
    }
}
