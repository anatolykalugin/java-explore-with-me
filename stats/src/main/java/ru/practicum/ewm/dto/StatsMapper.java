package ru.practicum.ewm.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.model.StatsClient;

@Component
public class StatsMapper {

    public static StatsClient toClass(StatsClientDto statsClientDto) {
        return StatsClient.builder()
                .id(statsClientDto.getId())
                .app(statsClientDto.getApp())
                .uri(statsClientDto.getUri())
                .ip(statsClientDto.getIp())
                .timestamp(statsClientDto.getTimestamp())
                .build();
    }

    public static StatsClientDto toDto(StatsClient statsClient) {
        return StatsClientDto.builder()
                .id(statsClient.getId())
                .app(statsClient.getApp())
                .uri(statsClient.getUri())
                .ip(statsClient.getIp())
                .timestamp(statsClient.getTimestamp())
                .build();
    }

}
