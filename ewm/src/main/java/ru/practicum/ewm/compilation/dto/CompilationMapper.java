package ru.practicum.ewm.compilation.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {

    public static Compilation toClass(CompilationCreationDto compilationDto, List<Event> eventList) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .events(eventList)
                .build();
    }

    public static CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::toCutDto)
                        .collect(Collectors.toList()))
                .build();
    }

}
