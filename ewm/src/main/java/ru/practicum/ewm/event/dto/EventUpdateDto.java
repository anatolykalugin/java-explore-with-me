package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Jacksonized
public class EventUpdateDto {
    Long eventId;
    String annotation;
    String title;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Long category;
    Boolean paid;
    Integer participantLimit;
}
