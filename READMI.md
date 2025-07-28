# Notification Service Application

Microservice for sending email notifications to users when creating or deleting accounts.

## Functionality

- **Kafka Consumer:** Processing the events of creating/removing users from the topic `user-service.user_events`
- **REST API:** provides endpoint for direct sending email notifications
- **Email Sending:** Uses Spring Mail to send SMTP letters
- **Validation:** checking the correctness of email addresses and operations

#### Docker container may be running for dev testing , it contain : Kafka, Mailhog
### Usage
- `http://localhost:8081/api/notifications/send` for test endpoint
- `localhost:9092` for Kafka
- `localhost:8025` for MailHog web UI
## Requirements


- **Configuration**: Spring setup via `application.yml`

## Requirement:
- Java JDK 21
- Kafka server running
- Docker for dev test