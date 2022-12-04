package ru.practicum.ewm.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationCreationDto {
    @NotBlank
    String title;
    Boolean pinned;
    List<Long> events;
}
