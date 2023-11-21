package subscriber.call.group.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subscriber.call.group.service.entity.Event;
import subscriber.call.group.service.service.EventService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${spring.webservices.path}")
public class StatsController {

    private final EventService eventService;


    @GetMapping("/stats")
    public ResponseEntity<Iterable<Event>> getStats() {

        var events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}
