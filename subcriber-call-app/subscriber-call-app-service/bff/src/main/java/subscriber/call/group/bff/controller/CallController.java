package subscriber.call.group.bff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subscriber.call.group.bff.dto.CallResponseDto;
import subscriber.call.group.bff.dto.stats.StatsDto;
import subscriber.call.group.bff.service.CallService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${spring.webservices.path}")
public class CallController {

    private final CallService callService;

    @PostMapping("/start-call/from/{initPhone}/to/{receivingPhone}")
    public ResponseEntity<CallResponseDto> startCall(@PathVariable long initPhone, @PathVariable long receivingPhone) throws JsonProcessingException {
        return ResponseEntity.ok(callService.startCall(initPhone, receivingPhone));
    }

    @PostMapping("/finish-call/from/{initPhone}/to/{receivingPhone}")
    public ResponseEntity<CallResponseDto> finishCall(@PathVariable long initPhone, @PathVariable long receivingPhone) throws JsonProcessingException {
        return ResponseEntity.ok(callService.finishCall(initPhone, receivingPhone));
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsDto> getStats() {
        return ResponseEntity.ok(callService.getStats());
    }

}