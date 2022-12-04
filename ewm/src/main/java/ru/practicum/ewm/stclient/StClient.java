package ru.practicum.ewm.stclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class StClient  {

    private final WebClient webClient;

    @Value("${app-name}")
    private String appName;

    public void saveStats(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        StatsClient statsClient = new StatsClient(appName, uri, ip);
        log.info(" УРИ в реквесте: " + uri);
        webClient.post()
                .uri("/hit")
                .body(BodyInserters.fromValue(statsClient))
                .retrieve()
                .bodyToMono(StatsClient.class)
                .block();
    }

}
