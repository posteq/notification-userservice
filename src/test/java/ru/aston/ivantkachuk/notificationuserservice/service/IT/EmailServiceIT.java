package ru.aston.ivantkachuk.notificationuserservice.service.IT;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.aston.ivantkachuk.notificationuserservice.entity.Operation;
import ru.aston.ivantkachuk.notificationuserservice.entity.UserEvent;
import ru.aston.ivantkachuk.notificationuserservice.service.EmailService;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailServiceIT {
    @Autowired
    private EmailService emailService;

    private GreenMail greenMail;

    @BeforeAll
    void startMailServer() {
        greenMail = new GreenMail();
        greenMail.start();
    }

    @AfterAll
    void stopMailServer() {
        greenMail.stop();
    }

    @AfterEach
    void resetMailServer() throws FolderException {
        greenMail.purgeEmailFromAllMailboxes();
    }

    @Test
    void shouldSendEmailCreateEvent() {
        UserEvent event = new UserEvent("user@example.com", Operation.CREATE);
        emailService.sendUserEventEmail(event);

        await().atMost(2, SECONDS).untilAsserted(() -> {
            MimeMessage[] messages = greenMail.getReceivedMessages();
            assertEquals(1, messages.length);

            MimeMessage msg = messages[0];
            assertEquals("Добро пожаловать!", msg.getSubject());
            assertTrue(((String) msg.getContent()).contains("аккаунт был успешно создан"));
        });
    }

    @Test
    void shouldSendEmailOnDeleteEvent() {
        UserEvent event = new UserEvent("user@example.com", Operation.DELETE);
        emailService.sendUserEventEmail(event);

        await().untilAsserted(() -> {
            MimeMessage msg = greenMail.getReceivedMessages()[0];
            assertEquals("Аккаунт удален", msg.getSubject());
        });
    }
}