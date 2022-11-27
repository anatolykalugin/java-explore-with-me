package ru.practicum.ewm.request.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.user.model.User;

@Component
public class RequestMapper {

    public static Request toClass(RequestDto requestDto, Event event, User author) {
        return Request.builder()
                .id(requestDto.getId())
                .event(event)
                .author(author)
                .state(requestDto.getState())
                .created(requestDto.getCreated())
                .build();
    }

    public static RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .author(request.getAuthor().getId())
                .state(request.getState())
                .created(request.getCreated())
                .build();
    }
}
