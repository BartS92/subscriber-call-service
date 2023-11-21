package subscriber.call.group.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subscriber.call.group.service.dto.SubscriberDto;
import subscriber.call.group.service.entity.Event;
import subscriber.call.group.service.entity.Subscriber;
import subscriber.call.group.service.service.EventService;
import subscriber.call.group.service.service.SubscriberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${spring.webservices.path}")
public class SubscriberController {

    private final SubscriberService subscriberService;


    @PostMapping("/subscriber")
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody SubscriberDto subscriberDto) {
        return  ResponseEntity.ok(subscriberService.createSubscriber(subscriberDto));
    }
}
