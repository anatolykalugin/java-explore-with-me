package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    void delete(Long id);

    List<UserDto> getUsers(List<Long> ids, Integer index, Integer size);

    UserDto getById(Long id);
}
