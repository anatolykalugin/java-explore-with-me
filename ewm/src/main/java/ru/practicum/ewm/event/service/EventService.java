package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.comment.dto.CommentDto;
import ru.practicum.ewm.event.dto.EventCreationDto;
import ru.practicum.ewm.event.dto.EventCutDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.dto.EventUpdateDto;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.request.dto.RequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto createEvent(Long userId, EventCreationDto eventDto);

    EventDto adminUpdate(Long eventId, EventCreationDto eventDto);

    List<EventCutDto> getPublicEvents(String text, List<Long> categoriesIds, LocalDateTime neededStart,
                                      LocalDateTime neededEnd, Boolean isPaid, Boolean available,
                                      Sort sort, Integer index, Integer size);

    EventDto getFullPublicEvent(Long eventId);

    List<EventDto> getAuthorsEvents(Long userId, Integer index, Integer size);

    EventDto updateOwnersEvent(Long userId, EventUpdateDto eventDto);

    EventDto getAuthorsEventById(Long userId, Long eventId);

    EventDto cancelAuthorsEvent(Long userId, Long eventId);

    List<EventDto> getAdminEvents(List<Long> users, List<State> states, List<Long> categories,
                                  LocalDateTime neededStart, LocalDateTime neededEnd, Integer index, Integer size);

    EventDto publishEvent(Long eventId);

    EventDto rejectEvent(Long eventId);

    List<RequestDto> getCurrentEventsUsersRequests(Long userId, Long eventId);

    RequestDto confirmUsersRequest(Long userId, Long eventId, Long requestId);

    RequestDto rejectUsersRequest(Long userId, Long eventId, Long requestId);

    CommentDto postComment(CommentDto commentDto, Long eventId, Long authorId);

    CommentDto editComment(CommentDto commentDto, Long authorId, Long commentId);

    void deleteCommentByAdmin(Long commentId);

    void deleteCommentByAuthor(Long commentId, Long authorId);

    CommentDto getCommentById(Long commentId);

    List<CommentDto> getEventComments(Long eventId, Integer index, Integer size);

}
