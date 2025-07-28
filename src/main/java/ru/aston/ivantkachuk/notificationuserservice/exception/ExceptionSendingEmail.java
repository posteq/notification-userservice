package ru.aston.ivantkachuk.notificationuserservice.exception;

public class ExceptionSendingEmail extends RuntimeException {
    public ExceptionSendingEmail(String message) {
        super(message);
    }
    public ExceptionSendingEmail(String message, Throwable cause) {
        super(message, cause);
    }
}
