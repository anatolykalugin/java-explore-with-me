package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventCreationDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.service.EventService;

import static ru.practicum.ewm.util.Constants.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAdminEvents(@RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) List<State> states,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(name = "rangeStart", required = false)
                                         @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime neededStart,
                                         @RequestParam(name = "rangeEnd", required = false)
                                         @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime neededEnd,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer index,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Event admin controller request: getting events");
        return eventService.getAdminEvents(users, states, categories, neededStart, neededEnd, index, size);
    }

    @PutMapping("/{eventId}")
    public EventDto adminUpdate(@PathVariable Long eventId, @RequestBody EventCreationDto eventDto) {
        log.info("Event admin controller request: updating an event");
        return eventService.adminUpdate(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventDto publishEvent(@PathVariable Long eventId) {
        log.info("Event admin controller request: publishing an event");
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventDto rejectEvent(@PathVariable Long eventId) {
        log.info("Event admin controller request: rejecting an event");
        return eventService.rejectEvent(eventId);
    }

}
