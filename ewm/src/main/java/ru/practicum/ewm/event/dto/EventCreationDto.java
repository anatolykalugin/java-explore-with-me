package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventCreationDto {
    Long id;
    @NotBlank
    String annotation;
    String title;
    @NotBlank
    String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startDate;
    @NotNull
    LocationDto location;
    @NotNull
    Long category;
    @NotNull
    Boolean paid;
    Integer participantLimit;
    Boolean moderation;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class LocationDto {
        private Double lat;
        private Double lon;
    }
}
