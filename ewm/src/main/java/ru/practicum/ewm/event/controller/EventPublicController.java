package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventCutDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.stclient.StClient;

import static ru.practicum.ewm.util.Constants.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final EventService eventService;
    private final StClient stClient;

    @GetMapping
    public List<EventCutDto> getPublicEvents(@RequestParam(required = false) String text,
                                             @RequestParam(name = "categories", required = false)
                                             List<Long> categoriesIds,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime neededStart,
                                             @RequestParam(name = "rangeEnd", required = false)
                                             @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime neededEnd,
                                             @RequestParam(name = "paid", required = false) Boolean paid,
                                             @RequestParam(name = "onlyAvailable", defaultValue = "false")
                                             Boolean available,
                                             @RequestParam(required = false) Sort sort,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                             Integer index,
                                             @Positive @RequestParam(defaultValue = "10") Integer size,
                                             HttpServletRequest httpServletRequest) {
        log.info("Event public controller request: getting public events");
        stClient.saveStats(httpServletRequest);
        return eventService.getPublicEvents(text, categoriesIds, neededStart, neededEnd, paid,
                available, sort, index, size);
    }

    @GetMapping("/{id}")
    public EventDto getFullPublicEvent(@PathVariable(name = "id") Long eventId,
                                       HttpServletRequest httpServletRequest) {
        log.info("Event public controller request: getting event's full info by id");
        stClient.saveStats(httpServletRequest);
        return eventService.getFullPublicEvent(eventId);
    }

}
