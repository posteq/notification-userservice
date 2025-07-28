package ru.aston.ivantkachuk.notificationuserservice.exception;

public class InvalidInputEventException extends RuntimeException {
    public InvalidInputEventException(String message) {
        super(message);
    }

    public InvalidInputEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
