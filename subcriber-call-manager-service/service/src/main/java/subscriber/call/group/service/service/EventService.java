package subscriber.call.group.service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.entity.Event;
import subscriber.call.group.service.repository.EventRepository;
import subscriber.call.group.service.util.Utils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;

    public Iterable<Event> getAllEvents() {
        return repository.findAll();
    }

    public Event createEvent(long initPhone, long receivedPhone, Status status) {
        var event = new Event();
        event.setInitPhone(initPhone);
        event.setReceivingPhone(receivedPhone);
        event.setStatus(status.toString());
        event.setDateCreated(Utils.getCurrentTimestamp());

        return event;
    }

    public Event createAndSave(long initPhone, long receivedPhone, Status status) {
        var event = createEvent(initPhone, receivedPhone, status);
        return repository.save(event);
    }

    public Event getLastPhoneEvent(long phone) {
        return repository.getLastPhoneEvent(phone);
    }

    public Event getLastCallEvent(long initPhone, long receivingPhone) {
        return repository.getLastCallEvent(initPhone, receivingPhone);
    }
}
