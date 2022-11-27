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
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new AlreadyExistsException("Уже есть категория с таким именем");
        } else {
            log.info("Валидация пройдена - сохранение категории");
            Category category = categoryRepository.save(CategoryMapper.toClass(categoryDto));
            return CategoryMapper.toDto(category);
        }
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("Запрос на обновление категории");
        if (categoryRepository.findById(categoryDto.getId()).isEmpty()) {
            throw new NotFoundException("Отсутствует категория для обновления");
        }
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new AlreadyExistsException("Уже есть категория с таким именем");
        }
        log.info("Валидация пройдена - обновляем категорию");
        Category category = categoryRepository.getReferenceById(categoryDto.getId());
        category.setName(category.getName());
        categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Запрос на удаление категории");
        if (categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Отсутствует категория для удаления");
        }
        if (categoryRepository.hasEventsByCategoryId(id)) {
            throw new AlreadyExistsException("Созданы ивенты с данной категорией, удаление невозможно");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getById(Long id) {
        log.info("Запрос на получение (и возможно последующие действия) категории");
        if (categoryRepository.findById(id).isPresent()) {
            return CategoryMapper.toDto(categoryRepository.getReferenceById(id));
        } else {
            throw new NotFoundException("Не найдена такая категория");
        }
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

}
