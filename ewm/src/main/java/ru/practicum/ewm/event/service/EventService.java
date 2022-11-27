package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventCutDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto createEvent(Long userId, EventDto eventDto);

    EventDto adminUpdate(Long eventId, EventDto eventDto);

    List<EventCutDto> getPublicEvents(String text, List<Long> categoriesIds, LocalDateTime neededStart,
                                      LocalDateTime neededEnd, Boolean available, Sort sort, Integer index,
                                      Integer size);

    EventDto getFullPublicEvent(Long eventId);

    List<EventDto> getAuthorsEvents(Long userId, Integer index, Integer size);

    EventDto updateOwnersEvent(Long userId, EventDto eventDto);

    EventDto getAuthorsEventById(Long userId, Long eventId);

    EventDto cancelAuthorsEvent(Long userId, Long eventId);

    List<EventDto> getAdminEvents(List<Long> users, List<State> states, List<Long> categories,
                                  LocalDateTime neededStart, LocalDateTime neededEnd, Integer index, Integer size);

    EventDto publishEvent(Long eventId);

    EventDto rejectEvent(Long eventId);

    //TODO: add 3 private request-connected methods
}
