package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventCutDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventPublicController {

    private final EventService eventService;

    @GetMapping
    public List<EventCutDto> getPublicEvents(@RequestParam(required = false) String text,
                                             @RequestParam(name = "categories", required = false)
                                             List<Long> categoriesIds,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime neededStart,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime neededEnd,
                                             @RequestParam(name = "onlyAvailable", defaultValue = "false")
                                             Boolean available,
                                             @RequestParam(required = false) Sort sort,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                             Integer index,
                                             @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getPublicEvents(text, categoriesIds, neededStart, neededEnd, available, sort, index, size);
    }

    @GetMapping("/{id}")
    public EventDto getFullPublicEvent(@PathVariable(name = "id") Long eventId) {
        return eventService.getFullPublicEvent(eventId);
    }

}
