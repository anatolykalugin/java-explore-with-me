package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
public class EventPrivateController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAuthorsEvents(@PathVariable Long userId,
                                           @PositiveOrZero
                                           @RequestParam(name = "from", defaultValue = "0") Integer index,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getAuthorsEvents(userId, index, size);
    }

    @PatchMapping
    public EventDto updateOwnersEvent(@PathVariable Long userId, @RequestBody EventUpdateDto eventDto) {
        return eventService.updateOwnersEvent(userId, eventDto);
    }

    @PostMapping
    public EventDto createEvent(@PathVariable Long userId, @Valid @RequestBody EventCreationDto eventDto) {
        return eventService.createEvent(userId, eventDto);
    }

    @GetMapping("/{eventId}")
    public EventDto getAuthorsEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.getAuthorsEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto cancelAuthorsEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.cancelAuthorsEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getCurrentEventsUsersRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.getCurrentEventsUsersRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmUsersRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                          @PathVariable(name = "reqId") Long requestId) {
        return eventService.confirmUsersRequest(userId, eventId, requestId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectUsersRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                         @PathVariable(name = "reqId") Long requestId) {
        return eventService.rejectUsersRequest(userId, eventId, requestId);
    }

}
