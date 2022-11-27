package ru.practicum.ewm.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String error) {
        super(error);
    }
}
