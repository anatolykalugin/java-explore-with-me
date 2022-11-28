package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.EventCreationDto;
import ru.practicum.ewm.event.dto.EventCutDto;
import ru.practicum.ewm.event.dto.EventDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.dto.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

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
    public EventDto adminUpdate(Long eventId, EventCreationDto eventDto) {
        log.info("Request for an event update (by Admin)");
        Event event = updateProcedure(retrieveEvent(eventId), eventDto);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public List<EventCutDto> getPublicEvents(String text, List<Long> categoriesIds, LocalDateTime neededStart,
                                             LocalDateTime neededEnd, Boolean available, Sort sort, Integer index,
                                             Integer size) {
        log.info("Request for getting public events");
        Pageable pageable = PageRequest.of(index / size, size);
        if (neededStart == null) {
            neededStart = LocalDateTime.now();
        }
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        List<EventCutDto> eventCutDtoList = eventRepository.getPublicEvents(text, categoriesIds, neededStart,
                        neededEnd, available, sort, pageable).stream()
                .map(EventMapper::toCutDto)
                .collect(Collectors.toList());
        if (available) {
            eventCutDtoList = eventCutDtoList.stream()
                    .filter(eventCutDto -> participationValidation(retrieveEvent(eventCutDto.getId())))
                    .collect(Collectors.toList());
        }
        if (sort != null) {
            switch (sort) {
                case VIEWS:
                    eventCutDtoList = eventCutDtoList.stream()
                            .sorted(Comparator.comparingInt(EventCutDto::getViews))
                            .collect(Collectors.toList());
                    break;
                case EVENT_DATE:
                    eventCutDtoList = eventCutDtoList.stream()
                            .sorted(Comparator.comparing(EventCutDto::getStartDate))
                            .collect(Collectors.toList());
                    break;
            }
        }
        return eventCutDtoList.stream()
                .skip(index)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getFullPublicEvent(Long eventId) {
        log.info("Request for getting an event (Public functionality)");
        retrieveEvent(eventId);
        log.info("Validation passed, getting...");
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED);
        return EventMapper.toDto(event);
    }

    @Override
    public List<EventDto> getAuthorsEvents(Long userId, Integer index, Integer size) {
        log.info("Request for getting initiator's events");
        Pageable pageable = PageRequest.of(index / size, size);
        return eventRepository.findAllByInitiatorId(userId, pageable).stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventDto updateOwnersEvent(Long userId, EventCreationDto eventDto) {
        log.info("Request for updating an event by the initiator");
        if (LocalDateTime.now().plusHours(2).isAfter(eventDto.getStartDate())) {
            throw new ValidationException("Start date too soon");
        }
        Event event = retrieveEvent(eventDto.getId());
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("No such user"));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("The user doesn't have access to this");
        }
        if (event.getState().equals(State.REJECTED) || event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Can't update an event in this state");
        }
        if (event.getState().equals(State.CANCELED)) {
            event.setState(State.PENDING);
        }
        return EventMapper.toDto(eventRepository.save(updateProcedure(event, eventDto)));
    }

    @Override
    public EventDto getAuthorsEventById(Long userId, Long eventId) {
        log.info("Request for getting an event's info by the initiator");
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        if (event != null) {
            return EventMapper.toDto(event);
        } else {
            throw new NotFoundException("Wrong user's/event's ID");
        }
    }

    @Transactional
    @Override
    public EventDto cancelAuthorsEvent(Long userId, Long eventId) {
        log.info("Request for an event cancellation");
        Event event = retrieveEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("The user doesn't have access to this");
        }
        if (!event.getState().equals(State.PENDING)) {
            throw new ValidationException("Can't cancel an event that is not pending");
        }
        event.setState(State.CANCELED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public List<EventDto> getAdminEvents(List<Long> users, List<State> states, List<Long> categories,
                                         LocalDateTime neededStart, LocalDateTime neededEnd, Integer index,
                                         Integer size) {
        log.info("Request for getting events (Admin functionality)");
        Pageable pageable = PageRequest.of(index / size, size);
        if (neededStart == null) {
            neededStart = LocalDateTime.now();
        }
        return eventRepository.getAdminEvents(users, states, categories, neededStart, neededEnd, pageable).stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventDto publishEvent(Long eventId) {
        log.info("Request for an event publishing");
        Event event = retrieveEvent(eventId);
        if (LocalDateTime.now().plusHours(1).isAfter(event.getStartDate()) ||
                !event.getState().equals(State.PENDING)) {
            throw new ValidationException("Validation of event publishing not passed");
        }
        log.info("Validation successful, publishing...");
        event.setState(State.PUBLISHED);
        event.setPublished(LocalDateTime.now());
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventDto rejectEvent(Long eventId) {
        log.info("Request for an event rejection");
        Event event = retrieveEvent(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("This event is already published");
        }
        log.info("Validation successful, rejecting...");
        event.setState(State.REJECTED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public List<RequestDto> getCurrentEventsUsersRequests(Long userId, Long eventId) {
        log.info("Request for getting event's requests");
        Event event = retrieveEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("The user doesn't have access to this");
        }
        return requestRepository.findAllByEventId(eventId).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDto confirmUsersRequest(Long userId, Long eventId, Long requestId) {
        log.info("Request for confirming an event's request");
        Event event = retrieveEvent(eventId);
        isUpdateValid(event, userId, requestId);
        if (participationValidation(event)) {
            throw new ValidationException("Participation limit reached");
        }
        log.info("Validation passed, confirming...");
        Request request = requestRepository.getReferenceById(requestId);
        request.setState(ru.practicum.ewm.request.model.State.CONFIRMED);
        if (participationValidation(event)) {
            event.getRequests().removeIf(event2 ->
                    !event2.getState().equals(ru.practicum.ewm.request.model.State.CONFIRMED));
        }
        eventRepository.save(event);
        return RequestMapper.toDto(request);
    }

    @Transactional
    @Override
    public RequestDto rejectUsersRequest(Long userId, Long eventId, Long requestId) {
        log.info("Request for rejecting an event's request");
        Event event = retrieveEvent(eventId);
        if (!userId.equals(event.getInitiator().getId())) {
            throw new ValidationException("Wrong user can't approve/reject");
        }
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found"));
        log.info("Validation passed, rejecting...");
        request.setState(ru.practicum.ewm.request.model.State.REJECTED);
        eventRepository.save(event);
        return RequestMapper.toDto(request);
    }

    private Event retrieveEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("No such event found"));
    }

    private Event updateProcedure(Event event, EventCreationDto newEvent) {
        if (newEvent.getAnnotation() != null) {
            event.setAnnotation(newEvent.getAnnotation());
        }
        if (newEvent.getCategory() != null) {
            event.setCategory(Category.builder().id(newEvent.getCategory()).build());
        }
        if (newEvent.getDescription() != null) {
            event.setDescription(newEvent.getDescription());
        }
        if (newEvent.getStartDate() != null) {
            event.setStartDate(newEvent.getStartDate());
        }
        if (newEvent.getPaid() != null) {
            event.setPaid(newEvent.getPaid());
        }
        if (newEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(newEvent.getParticipantLimit());
        }
        if (newEvent.getTitle() != null) {
            event.setTitle(newEvent.getTitle());
        }
        if (newEvent.getModeration() != null) {
            event.setModeration(newEvent.getModeration());
        }
        if (newEvent.getLocation() != null) {
            event.setLat(newEvent.getLocation().getLat());
            event.setLon(newEvent.getLocation().getLon());
        }
        return event;
    }

    private void isUpdateValid(Event event, Long userId, Long requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with such ID is absent"));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with such ID is absent"));
        if (!request.getEvent().getId().equals(event.getId())) {
            throw new ValidationException("Wrong event/request link");
        }
        if (!request.getState().equals(ru.practicum.ewm.request.model.State.PENDING)) {
            throw new ValidationException("Status is not Pending");
        }
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ValidationException("Wrong user can't approve/reject");
        }
        if (!event.getModeration() || (event.getParticipantLimit() == 0)) {
            throw new ValidationException("No need to confirm the request");
        }
    }

    private boolean participationValidation(Event event) {
        long confirmedParticipants = event.getRequests().stream()
                .filter(event2 -> event2.getState().equals(ru.practicum.ewm.request.model.State.CONFIRMED)).count();
        return confirmedParticipants >= event.getParticipantLimit();
    }

}