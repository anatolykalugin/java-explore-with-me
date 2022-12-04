package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.StatsClientDto;
import ru.practicum.ewm.model.Stats;
import ru.practicum.ewm.service.StatsService;

import static ru.practicum.ewm.util.Constants.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public StatsClientDto save(@Valid @RequestBody StatsClientDto statsClientDto) {
        log.info("Stats controller request: saving stats");
        log.info(" УРИ в контроллере Статов: " + statsClientDto.getUri());
        return statsService.saveStats(statsClientDto);
    }

    @GetMapping("/stats")
    public List<Stats> get(@RequestParam @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime start,
                           @RequestParam @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime end,
                           @RequestParam(required = false) List<String> uris,
                           @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Stats controller request: getting stats");
        return statsService.getStats(start, end, uris, unique);
    }

}
