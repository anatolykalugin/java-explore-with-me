package ru.practicum.ewm.event.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@Component
public class EventMapper {
    public static Event toClass(EventCreationDto eventDto, Long userId) {
        return Event.builder()
                .id(eventDto.getId())
                .annotation(eventDto.getAnnotation())
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .state(State.PENDING)
                .created(LocalDateTime.now())
                .eventDate(eventDto.getEventDate())
                .initiator(User.builder()
                        .id(userId)
                        .build())
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .category(Category.builder()
                        .id(eventDto.getCategory())
                        .build())
                .paid(eventDto.getPaid())
                .moderation(eventDto.getRequestModeration())
                .participantLimit(eventDto.getParticipantLimit())
                .build();
    }

    public static EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .description(event.getDescription())
                .state(event.getState())
                .createdOn(event.getCreated())
                .publishedOn(event.getPublished())
                .eventDate(event.getEventDate())
                .initiator(EventDto.UserCutDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .location(EventDto.LocationDto.builder()
                        .lat(event.getLat())
                        .lon(event.getLon())
                        .build())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .paid(event.getPaid())
                .requestModeration(event.getModeration())
                .participantLimit(event.getParticipantLimit())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

    public static EventCutDto toCutDto(Event event) {
        return EventCutDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .initiator(event.getInitiator().getId())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .paid(event.getPaid())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }
}
