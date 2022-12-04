package ru.practicum.ewm.category.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Category;

@Component
public class CategoryMapper {

    public static Category toClass(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
