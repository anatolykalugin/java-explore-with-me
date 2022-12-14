package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.model.State;

import static ru.practicum.ewm.util.Constants.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventDto {
    Long id;
    @NotBlank
    String annotation;
    @NotNull
    String title;
    String description;
    State state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
    LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
    LocalDateTime publishedOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
    LocalDateTime eventDate;
    @NotNull
    UserCutDto initiator;
    @NotNull
    LocationDto location;
    @NotNull
    CategoryDto category;
    @NotNull
    Boolean paid;
    Boolean requestModeration;
    Integer participantLimit;
    Integer views;
    Integer confirmedRequests;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserCutDto {
        Long id;
        String name;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LocationDto {
        Double lat;
        Double lon;
    }
}
