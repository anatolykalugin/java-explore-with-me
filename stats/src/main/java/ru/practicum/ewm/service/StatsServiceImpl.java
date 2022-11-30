package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.StatsClientDto;
import ru.practicum.ewm.dto.StatsMapper;
import ru.practicum.ewm.model.Stats;
import ru.practicum.ewm.model.StatsClient;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Transactional
    @Override
    public StatsClientDto saveStats(StatsClientDto statsClientDto) {
        StatsClient statsClient = StatsMapper.toClass(statsClientDto);
        statsClient.setTimestamp(LocalDateTime.now());
        log.info("MAPPED");
        statsRepository.save(statsClient);
        log.info("SAVED");
        return StatsMapper.toDto(statsClient);
    }

    @Override
    public List<Stats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            log.info("GETTING UNIQUE");
            return statsRepository.getAllUniqueStats(start, end, uris, true);
        } else {
            log.info("GETTING NOT UNIQUE");
            return statsRepository.getAllStats(start, end, uris);
        }
    }

}
