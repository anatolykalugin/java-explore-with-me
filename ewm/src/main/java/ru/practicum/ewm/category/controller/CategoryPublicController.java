package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryPublicController {

    private final CategoryService categoryService;


    @GetMapping("/{catId}")
    public CategoryDto getById(@NotNull @PathVariable(name = "catId") Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                              Integer index,
                                              @Positive @RequestParam(defaultValue = "10") Integer size) {
        return categoryService.getAllCategories(index, size);
    }

}
