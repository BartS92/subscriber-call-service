package subscriber.call.group.service.service;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.service.dto.SubscriberDto;
import subscriber.call.group.service.entity.Subscriber;
import subscriber.call.group.service.repository.SubscriberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class SubscriberServiceTest {
    @Mock
    private SubscriberRepository repository;

    private SubscriberService testObj;

    @BeforeEach
    void setUp() throws Exception {
        var sub1 = new Subscriber();
        sub1.setId(123456l);
        sub1.setName("John");

        var sub2 = new Subscriber();
        sub2.setId(234567l);
        sub2.setName("Kenny");

        when(repository.findById(123456l)).thenReturn(sub1);
        when(repository.findById(234567l)).thenReturn(sub2);

        when(repository.findAll()).thenReturn(new ArrayList<>() {{add(sub1); add(sub2);}});

        testObj = new SubscriberService(repository);
    }

    @Test
    void getSubscriber_whenGiveValidId_thenReturnSubscriber() {
        var subs = testObj.getSubscriber(123456l);
        assertEquals(123456l, subs.getId());
        assertEquals("John", subs.getName());
    }

    @Test
    void createSubscriber_whenGiveValidNumber_thenReturnSubscriber() {
        var sub = new Subscriber();
        sub.setId(333333l);
        sub.setName("Name");
        when(repository.save(sub)).thenReturn(sub);


        var dto = new SubscriberDto();
        dto.setPhone(333333l);
        dto.setName("Name");
        var newSub = testObj.createSubscriber(dto);
        assertEquals(333333l, newSub.getId());
        assertEquals("Name", newSub.getName());
    }
}
