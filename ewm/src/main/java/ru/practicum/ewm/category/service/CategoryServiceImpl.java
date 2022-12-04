package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.exception.EventAlreadyExistsException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        log.info("Запрос на создание категории");
        isNameValid(categoryDto.getName());
        log.info("Валидация пройдена - сохранение категории");
        Category category = categoryRepository.save(CategoryMapper.toClass(categoryDto));
        return CategoryMapper.toDto(category);
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("Запрос на обновление категории");
        isIdValid(categoryDto.getId());
        isNameValid(categoryDto.getName());
        log.info("Валидация пройдена - обновляем категорию");
        Category category = categoryRepository.getReferenceById(categoryDto.getId());
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Запрос на удаление категории");
        isIdValid(id);
        if (categoryRepository.hasEventsByCategoryId(id)) {
            throw new EventAlreadyExistsException("Созданы ивенты с данной категорией, удаление невозможно");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getById(Long id) {
        log.info("Запрос на получение (и возможно последующие действия) категории");
        isIdValid(id);
        return CategoryMapper.toDto(categoryRepository.getReferenceById(id));
    }

    @Override
    public List<CategoryDto> getAllCategories(Integer index, Integer size) {
        log.info("Запрос на получение всех категорий");
        Pageable pageable = PageRequest.of(index / size, size);
        return categoryRepository.findAll(pageable)
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    private void isNameValid(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new AlreadyExistsException("Category with such name already exists");
        }
    }

    private void isIdValid(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Category with such ID is missing");
        }
    }

}
