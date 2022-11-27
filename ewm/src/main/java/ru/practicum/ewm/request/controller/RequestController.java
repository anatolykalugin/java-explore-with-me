package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public RequestDto createRequest(@PathVariable @NotNull Long userId, @RequestParam Long eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable @NotNull Long userId,
                                    @PathVariable @NotNull Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }


    @GetMapping
    public List<RequestDto> getUsersRequests(@PathVariable @NotNull Long userId) {
        return requestService.getUsersRequests(userId);
    }

}
