package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dto.EventCreationDto;
import ru.practicum.ewm.event.dto.EventCutDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.dto.RequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Transactional
    @Override
    public EventDto createEvent(Long userId, EventCreationDto eventDto) {
        log.info("Request for an event creation");
        if (LocalDateTime.now().plusHours(2).isAfter(eventDto.getStartDate())) {
            throw new ValidationException("Wrong event start date (too soon)");
        }
        return EventMapper.toDto(eventRepository.save(EventMapper.toClass(eventDto, userId)));
    }

    @Transactional
    @Override
    public EventDto adminUpdate(Long eventId, EventDto eventDto) {

    }

    @Override
    public List<EventCutDto> getPublicEvents(String text, List<Long> categoriesIds, LocalDateTime neededStart,
                                             LocalDateTime neededEnd, Boolean available, Sort sort, Integer index,
                                             Integer size) {

    }

    @Override
    public EventDto getFullPublicEvent(Long eventId) {

    }

    @Override
    public List<EventDto> getAuthorsEvents(Long userId, Integer index, Integer size) {

    }

    @Transactional
    @Override
    public EventDto updateOwnersEvent(Long userId, EventDto eventDto) {

    }

    @Override
    public EventDto getAuthorsEventById(Long userId, Long eventId) {

    }

    @Transactional
    @Override
    public EventDto cancelAuthorsEvent(Long userId, Long eventId) {

    }

    @Override
    public List<EventDto> getAdminEvents(List<Long> users, List<State> states, List<Long> categories,
                                         LocalDateTime neededStart, LocalDateTime neededEnd, Integer index, Integer size) {

    }

    @Transactional
    @Override
    public EventDto publishEvent(Long eventId) {

    }

    @Transactional
    @Override
    public EventDto rejectEvent(Long eventId) {

    }

    @Override
    public List<RequestDto> getCurrentEventsUsersRequests(Long userId, Long eventId) {

    }

    @Transactional
    @Override
    public RequestDto confirmUsersRequest(Long userId, Long eventId, Long requestId) {

    }

    @Transactional
    @Override
    public RequestDto rejectUsersRequest(Long userId, Long eventId, Long requestId) {

    }

}
