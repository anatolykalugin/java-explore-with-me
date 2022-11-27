package ru.practicum.ewm.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.category.dto.CategoryDto;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventCutDto {
    Long id;
    String annotation;
    String title;
    LocalDateTime startDate;
    Long initiatorId;
    CategoryDto category;
    Boolean paid;
}
