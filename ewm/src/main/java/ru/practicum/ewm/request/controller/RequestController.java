package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public RequestDto createRequest(@PathVariable @NotNull Long userId, @RequestParam Long eventId) {
        log.info("Participation requests' controller request: creating a request");
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable @NotNull Long userId,
                                    @PathVariable @NotNull Long requestId) {
        log.info("Participation requests' controller request: cancelling a request");
        return requestService.cancelRequest(userId, requestId);
    }


    @GetMapping
    public List<RequestDto> getUsersRequests(@PathVariable @NotNull Long userId) {
        log.info("Participation requests' controller request: getting all user's requests");
        return requestService.getUsersRequests(userId);
    }

}
