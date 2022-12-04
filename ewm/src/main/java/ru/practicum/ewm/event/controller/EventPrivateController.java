package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.comment.dto.CommentDto;
import ru.practicum.ewm.event.dto.EventCreationDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.dto.EventUpdateDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.RequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAuthorsEvents(@PathVariable Long userId,
                                           @PositiveOrZero
                                           @RequestParam(name = "from", defaultValue = "0") Integer index,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Event private controller request: getting initiator's events");
        return eventService.getAuthorsEvents(userId, index, size);
    }

    @PatchMapping
    public EventDto updateOwnersEvent(@PathVariable Long userId, @RequestBody EventUpdateDto eventDto) {
        log.info("Event private controller request: updating initiator's event");
        return eventService.updateOwnersEvent(userId, eventDto);
    }

    @PostMapping
    public EventDto createEvent(@PathVariable Long userId, @Valid @RequestBody EventCreationDto eventDto) {
        log.info("Event private controller request: creating an event");
        return eventService.createEvent(userId, eventDto);
    }

    @GetMapping("/{eventId}")
    public EventDto getAuthorsEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Event private controller request: getting initiator's event by id");
        return eventService.getAuthorsEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto cancelAuthorsEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Event private controller request: cancelling initiator's event");
        return eventService.cancelAuthorsEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getCurrentEventsUsersRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Event private controller request: getting initiator's events participation requests");
        return eventService.getCurrentEventsUsersRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmUsersRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                          @PathVariable(name = "reqId") Long requestId) {
        log.info("Event private controller request: confirming a participation request");
        return eventService.confirmUsersRequest(userId, eventId, requestId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectUsersRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                         @PathVariable(name = "reqId") Long requestId) {
        log.info("Event private controller request: rejecting a participation request");
        return eventService.rejectUsersRequest(userId, eventId, requestId);
    }

    @PostMapping("/{eventId}/comments")
    public CommentDto postComment(@Valid @RequestBody CommentDto commentDto,
                                  @PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Event private controller request: posting a comment");
        return eventService.postComment(commentDto, eventId, userId);
    }

    @PatchMapping("/comments/{comId}")
    public CommentDto editComment(@Valid @RequestBody CommentDto commentDto,
                                  @PathVariable Long userId, @PathVariable(name = "comId") Long commentId) {
        log.info("Event private controller request: editing a comment");
        return eventService.editComment(commentDto, userId, commentId);
    }

    @DeleteMapping("/comments/{comId}")
    public void deleteCommentByAuthor(@PathVariable Long userId, @PathVariable(name = "comId") Long commentId) {
        log.info("Event private controller request: deleting a comment by an author");
        eventService.deleteCommentByAuthor(commentId, userId);
    }

}
