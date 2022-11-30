package ru.practicum.ewm.stclient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsClient {
    Long id;
    @NotBlank
    String app;
    @NotBlank
    String uri;
    @NotBlank
    String ip;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

    public StatsClient(String app, String uri, String ip) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
    }

}
