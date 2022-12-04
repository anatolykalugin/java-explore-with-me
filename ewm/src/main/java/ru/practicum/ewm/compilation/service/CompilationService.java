package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationCreationDto;
import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getAllCompilations(Boolean pinned, Integer index, Integer size);

    CompilationDto getCompilationById(Long compilationId);

    CompilationDto createCompilation(CompilationCreationDto compilationCreationDto);

    CompilationDto addEventToCompilation(Long compilationId, Long eventId);

    CompilationDto pinCompilation(Long compilationId);

    void deleteCompilation(Long compilationId);

    void deleteEventFromCompilation(Long compilationId, Long eventId);

    void unpinCompilation(Long compilationId);

}
