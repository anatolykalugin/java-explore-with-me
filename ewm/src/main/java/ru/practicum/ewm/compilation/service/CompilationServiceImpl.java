package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationCreationDto;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto createCompilation(CompilationCreationDto compilationCreationDto) {
        log.info("Request for a compilation creation");
        List<Event> events = eventRepository.getByIdIn(compilationCreationDto.getEvents());
        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toClass(compilationCreationDto,
                events)));
    }

    @Transactional
    @Override
    public CompilationDto addEventToCompilation(Long compilationId, Long eventId) {
        log.info("Request for adding an event to a compilation");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("No such event"));
        Compilation compilation = retrieveCompilation(compilationId);
        List<Event> events = compilation.getEvents();
        for (Event event1 : events) {
            if (event1.equals(event)) {
                throw new EventAlreadyExistsException("This event is already in the compilation");
            }
        }
        log.info("Validation passed, adding an event...");
        events.add(event);
        compilation.setEvents(events);
        return CompilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public CompilationDto pinCompilation(Long compilationId) {
        log.info("Request for pinning a compilation");
        Compilation compilation = retrieveCompilation(compilationId);
        if (compilation.getPinned()) {
            throw new CompilationAlreadyExistsException("Compilation is already pinned");
        }
        log.info("Compilation found, pinning...");
        compilation.setPinned(true);
        return CompilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public void deleteCompilation(Long compilationId) {
        log.info("Request for deleting a compilation");
        Compilation compilation = retrieveCompilation(compilationId);
        log.info("Compilation found, deleting...");
        compilationRepository.delete(compilation);
    }

    @Transactional
    @Override
    public void deleteEventFromCompilation(Long compilationId, Long eventId) {
        log.info("Request for deleting an event from a compilation");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("No such event"));
        Compilation compilation = retrieveCompilation(compilationId);
        List<Event> events = compilation.getEvents();
        for (Event event1 : events) {
            if (event1.equals(event)) {
                log.info("Validation passed, removing an event...");
                events.remove(event);
                compilation.setEvents(events);
                compilationRepository.save(compilation);
                return;
            }
        }
        throw new EventNotFoundException("This event is not in the compilation");
    }

    @Transactional
    @Override
    public void unpinCompilation(Long compilationId) {
        log.info("Request for unpinning a compilation");
        Compilation compilation = retrieveCompilation(compilationId);
        if (!compilation.getPinned()) {
            throw new CompilationAlreadyExistsException("Compilation is already unpinned");
        }
        log.info("Compilation found, unpinning...");
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer index, Integer size) {
        log.info("Request for getting all compilations");
        Pageable pageable = PageRequest.of(index / size, size);
        if (pinned == null) {
            return compilationRepository.findAll(pageable)
                    .stream()
                    .map(CompilationMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return compilationRepository.findAllByPinned(pinned, pageable)
                    .stream()
                    .map(CompilationMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {
        log.info("Request for getting a compilation");
        Compilation compilation = retrieveCompilation(compilationId);
        log.info("Compilation found, getting it...");
        return CompilationMapper.toDto(compilation);
    }

    private Compilation retrieveCompilation(Long id) {
        return compilationRepository.findById(id).orElseThrow(() -> new NotFoundException("No such compilation found"));
    }

}
