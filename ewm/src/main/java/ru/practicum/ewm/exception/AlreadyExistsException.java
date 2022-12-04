package ru.practicum.ewm.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String error) {
        super(error);
    }
}
