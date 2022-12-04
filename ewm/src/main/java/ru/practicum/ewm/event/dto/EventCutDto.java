package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.category.dto.CategoryDto;

import static ru.practicum.ewm.util.Constants.*;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
    LocalDateTime eventDate;
    Long initiator;
    CategoryDto category;
    Boolean paid;
    Integer views;
    Integer confirmedRequests;
}
