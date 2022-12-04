package ru.practicum.ewm.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String error) {
        super(error);
    }
}
