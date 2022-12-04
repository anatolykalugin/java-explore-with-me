package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationCreationDto;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody @Valid CompilationCreationDto compilationCreationDto) {
        log.info("Compilation admin controller request: creating a compilation");
        return compilationService.createCompilation(compilationCreationDto);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public CompilationDto addEventToCompilation(@PathVariable @NotNull Long compId,
                                                @PathVariable @NotNull Long eventId) {
        log.info("Compilation admin controller request: adding event to a compilation");
        return compilationService.addEventToCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public CompilationDto pinCompilation(@PathVariable @NotNull Long compId) {
        log.info("Compilation admin controller request: pinning a compilation");
        return compilationService.pinCompilation(compId);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable @NotNull Long compId) {
        log.info("Compilation admin controller request: deleting a compilation");
        compilationService.deleteCompilation(compId);
    }


    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable @NotNull Long compId,
                                           @PathVariable @NotNull Long eventId) {
        log.info("Compilation admin controller request: deleting an event from a compilation");
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable @NotNull Long compId) {
        log.info("Compilation admin controller request: unpinning a compilation");
        compilationService.unpinCompilation(compId);
    }

}
