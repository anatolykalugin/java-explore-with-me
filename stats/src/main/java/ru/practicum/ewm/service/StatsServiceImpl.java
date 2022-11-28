package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.StatsClientDto;
import ru.practicum.ewm.dto.StatsMapper;
import ru.practicum.ewm.model.Stats;
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
    public StatsClientDto save(StatsClientDto statsClientDto) {
        return StatsMapper.toDto(statsRepository.save(StatsMapper.toClass(statsClientDto)));
    }

    @Override
    public List<Stats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return statsRepository.getAllUniqueStats(start, end, uris, true);
        } else {
            return statsRepository.getAllStats(start, end, uris);
        }
    }

}
