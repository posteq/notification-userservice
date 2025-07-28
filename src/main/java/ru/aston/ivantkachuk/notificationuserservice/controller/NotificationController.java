package ru.aston.ivantkachuk.notificationuserservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.ivantkachuk.notificationuserservice.dto.EmailResponseDto;
import ru.aston.ivantkachuk.notificationuserservice.dto.UserEventRequestDto;
import ru.aston.ivantkachuk.notificationuserservice.service.EmailProxyService;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailProxyService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailResponseDto> sendNotification(@Valid @RequestBody UserEventRequestDto event) {
        return ResponseEntity.ok(emailService.sendEmail(event));
    }
}
