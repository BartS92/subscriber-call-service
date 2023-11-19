package subscriber.call.group.service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import subscriber.call.group.service.dto.SubscriberDto;
import subscriber.call.group.service.entity.Subscriber;
import subscriber.call.group.service.repository.SubscriberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository repository;

    public Subscriber getSubscriber(long phone) {
        return repository.findById(phone);
    }

    public Subscriber createSubscriber(SubscriberDto subscriberDto) {
        var subscriber = new Subscriber();
        subscriber.setId(subscriberDto.getPhone());
        subscriber.setName(subscriberDto.getName());
        return repository.save(subscriber);
    }

}

