package ru.practicum.ewm.exception;

public class EventAlreadyExistsException extends AlreadyExistsException {
    public EventAlreadyExistsException(String error) {
        super(error);
    }
}
