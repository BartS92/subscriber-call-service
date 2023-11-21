package subscriber.call.group.bff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subscriber.call.group.bff.dto.stats.StatsDto;
import subscriber.call.group.bff.service.StatsService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${spring.webservices.path}")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/stats")
    public ResponseEntity<StatsDto> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }

}
