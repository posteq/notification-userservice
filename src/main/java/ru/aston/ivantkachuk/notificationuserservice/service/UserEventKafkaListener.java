package ru.aston.ivantkachuk.notificationuserservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.aston.ivantkachuk.notificationuserservice.entity.UserEvent;
import ru.aston.ivantkachuk.notificationuserservice.exception.InvalidInputEventException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventKafkaListener {

    private final EmailService emailService;

    @KafkaListener(
            topics = "${spring.kafka.topics.user-events.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleUserEvent(String json) {
        log.info("Received user event: {}", json);
        try {
            UserEvent userEvent = new ObjectMapper().readValue(json, UserEvent.class);
            checkInputEvent(userEvent);
            emailService.sendUserEventEmail(userEvent);
            log.info("The event is successfully processed: {}", userEvent.getOperation());
        }catch (Exception e){
            log.error("Error when processing user events : {}", e);
        }
    }

    private void checkInputEvent(UserEvent userEvent) throws InvalidInputEventException {
        if (userEvent == null || userEvent.getEmail() == null || userEvent.getOperation() == null) {
            log.error("An incorrect event has been received: {}", userEvent);
            throw new InvalidInputEventException("Email invalid , input event : " + userEvent);
        }

    }
}
