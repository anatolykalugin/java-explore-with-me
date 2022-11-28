package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Stats;
import ru.practicum.ewm.model.StatsClient;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<StatsClient, Long> {

    @Query("SELECT new ru.practicum.ewm.model.Stats(s.app, s.uri, COUNT (s.ip)) " +
            "from StatsClient s WHERE s.timestamp> ?1 AND s.timestamp< ?2 GROUP BY s.app, s.uri")
    List<Stats> getAllStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.ewm.model.Stats(s.app, s.uri, COUNT (DISTINCT s.ip)) from " +
            "StatsClient s WHERE s.timestamp> ?1 AND s.timestamp< ?2 GROUP BY s.app, s.uri")
    List<Stats> getAllUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
