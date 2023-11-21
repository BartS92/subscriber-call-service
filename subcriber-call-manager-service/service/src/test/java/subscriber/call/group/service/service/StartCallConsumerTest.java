package subscriber.call.group.service.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.service.TestUtils;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallDto;
import subscriber.call.group.service.entity.Event;
import subscriber.call.group.service.entity.Subscriber;
import subscriber.call.group.service.service.rabbit.StartCallConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class StartCallConsumerTest {
    private StartCallConsumer testObj;

    @Mock
    private EventService eventService;

    @Mock
    private SubscriberService subscriberService;

    @BeforeEach
    void setUp() throws Exception {
        var subs1 = new Subscriber();
        subs1.setId(123456l);
        subs1.setName("Name1");

        var subs2 = new Subscriber();
        subs2.setId(234567l);
        subs2.setName("Name2");

        when(subscriberService.getSubscriber(123456l)).thenReturn(subs1);
        when(subscriberService.getSubscriber(234567l)).thenReturn(subs2);
        testObj = new StartCallConsumer(eventService, subscriberService);
    }


    @Test
    void receiveAndReply_whenReceivingPhoneDoesntExist_thenReturnNotFound() {
        var dto = new CallDto(123456l, 2345699l);
        var callResponseDto = TestUtils.convertToCallResponse(testObj.receiveAndReply(dto));
        assertNotNull(callResponseDto);
        assertEquals(Status.NOT_FOUND, callResponseDto.getStatus());
        assertEquals("2345699 number is not found", callResponseDto.getMessage());
    }

    @Test
    void receiveAndReply_whenStartCallFromInitSubscriber_thenReturnFinishedReply() {
        var dto = new CallDto(123456l, 234567l);
        var callResponseDto = TestUtils.convertToCallResponse(testObj.receiveAndReply(dto));
        assertNotNull(callResponseDto);
        assertEquals(Status.STARTED, callResponseDto.getStatus());
    }

    @Test
    void receiveAndReply_whenReceivingPhoneIsBusy_thenReturnBusyReply() {
        var event = new Event();
        event.setInitPhone(123456l);
        event.setReceivingPhone(234567l);
        event.setStatus("STARTED");
        when(eventService.getLastPhoneEvent(234567l)).thenReturn(event);

        var dto = new CallDto(323456l, 234567l);
        var callResponseDto = TestUtils.convertToCallResponse(testObj.receiveAndReply(dto));
        assertNotNull(callResponseDto);
        assertEquals(Status.BUSY, callResponseDto.getStatus());
        assertEquals("234567 number is busy", callResponseDto.getMessage());
    }
}
