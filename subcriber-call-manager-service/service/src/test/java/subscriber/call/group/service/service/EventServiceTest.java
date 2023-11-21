package subscriber.call.group.service.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.entity.Event;
import subscriber.call.group.service.repository.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {

    @Mock
    private EventRepository repository;

    private EventService testObj;

    @BeforeEach
    void setUp() throws Exception {
        var event1 = new Event();
        event1.setId(1l);
        event1.setStatus("STARTED");
        event1.setInitPhone(123456l);
        event1.setReceivingPhone(234567l);
        event1.setDateCreated(new Date(1700571169440l));

        var event2 = new Event();
        event2.setId(2l);
        event2.setStatus("FINISHED");
        event2.setInitPhone(123456l);
        event2.setReceivingPhone(234567l);
        event2.setDateCreated(new Date(1700571176526l));

        when(repository.findById(1)).thenReturn(event1);
        when(repository.findById(2)).thenReturn(event2);

        when(repository.findAll()).thenReturn(new ArrayList<>() {{add(event1); add(event2);}});

        testObj = new EventService(repository);
    }

    @Test
    void createEventAndSave_whenGiveValidData_thenReturnEvent() {
        var event = new Event();
        event.setId(3l);
        event.setDateCreated(new Date(1700571232126l));
        event.setStatus("STARTED");
        event.setInitPhone(333333l);
        event.setReceivingPhone(444444l);
        when(repository.save(any())).thenReturn(event);

        var createdEvent = testObj.createAndSave(333333l, 444444l, Status.STARTED);
        assertEquals(event.getStatus(), createdEvent.getStatus());
        assertEquals(event.getInitPhone(), createdEvent.getInitPhone());
        assertEquals(event.getReceivingPhone(), createdEvent.getReceivingPhone());
        assertEquals(event.getDateCreated(), createdEvent.getDateCreated());
    }

    @Test
    void getLastCallEvent_whenGiveValidId_thenReturnEvent() {
        var event2 = new Event();
        event2.setId(2l);
        event2.setStatus("FINISHED");
        event2.setInitPhone(123456l);
        event2.setReceivingPhone(234567l);
        event2.setDateCreated(new Date(1700571176526l));

        when(repository.getLastCallEvent(123456l, 234567l)).thenReturn(event2);
        var event = testObj.getLastCallEvent(123456l, 234567l);

        assertEquals(2, event.getId());
        assertEquals("FINISHED", event.getStatus());
        assertEquals(123456l, event.getInitPhone());
        assertEquals(234567l, event.getReceivingPhone());
    }


}
