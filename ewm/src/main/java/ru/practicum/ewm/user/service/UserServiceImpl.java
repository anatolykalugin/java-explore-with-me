package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        log.info("Запрос на создание юзера");
        if (userRepository.findByName(userDto.getName()).isPresent()) {
            throw new AlreadyExistsException("User with this name already exists");
        }
        User user = userRepository.save(UserMapper.toClass(userDto));
        return UserMapper.toDto(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Запрос на удаление юзера");
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден.")));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer index, Integer size) {
        log.info("Запрос на получение многих юзеров");
        Pageable pageable = PageRequest.of(index / size, size);
        if (ids.isEmpty()) {
            return userRepository.findAll(pageable)
                    .stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAllByIdIn(ids, pageable)
                    .stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto getById(Long id) {
        log.info("Поиск юзера по айди");
        return UserMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден.")));
    }

}
