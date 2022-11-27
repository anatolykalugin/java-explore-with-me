package ru.practicum.ewm.event.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

@Component
public class EventMapper {
    public static Event toClass(EventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .annotation(eventDto.getAnnotation())
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .state(eventDto.getState())
                .created(eventDto.getCreated())
                .published(eventDto.getPublished())
                .startDate(eventDto.getStartDate())
                .initiator(User.builder()
                        .id(eventDto.getInitiator().getId())
                        .build())
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .category(Category.builder()
                        .id(eventDto.getCategory().getId())
                        .build())
                .paid(eventDto.getPaid())
                .moderation(eventDto.getModeration())
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
                .created(event.getCreated())
                .published(event.getPublished())
                .startDate(event.getStartDate())
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
                .moderation(event.getModeration())
                .participantLimit(event.getParticipantLimit())
                .build();
    }

    public static EventCutDto toCutDto(Event event) {
        return EventCutDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .startDate(event.getStartDate())
                .initiatorId(event.getInitiator().getId())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .paid(event.getPaid())
                .build();
    }
}
