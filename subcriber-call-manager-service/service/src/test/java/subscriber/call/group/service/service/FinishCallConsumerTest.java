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
import subscriber.call.group.service.service.rabbit.FinishCallConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
class FinishCallConsumerTest {

    private FinishCallConsumer testObj;

    @Mock
    private EventService eventService;

    @BeforeEach
    void setUp() throws Exception {
        var event = new Event();
        event.setInitPhone(123456l);
        event.setReceivingPhone(234567l);
        event.setStatus("STARTED");
        when(eventService.getLastCallEvent(123456l, 234567l)).thenReturn(event);

        testObj = new FinishCallConsumer(eventService);
    }


    @Test
    void receiveAndReply_whenFinishCallFromInitSubscriber_thenReturnFinishedReply() {
        var dto = new CallDto(123456l, 234567l);
        var callResponseDto = TestUtils.convertToCallResponse(testObj.receiveAndReply(dto));
        assertNotNull(callResponseDto);
        assertEquals(Status.FINISHED, callResponseDto.getStatus());
    }

    @Test
    void receiveAndReply_whenFinishCallFromAnotherSubscriber_thenReturnNoStatus() {
        var dto = new CallDto(323456l, 234567l);
        var callResponseDto = TestUtils.convertToCallResponse(testObj.receiveAndReply(dto));
        assertNotNull(callResponseDto);
        assertEquals(Status.NO_STATUS, callResponseDto.getStatus());
        assertEquals("No active calls between 323456 and 234567", callResponseDto.getMessage());
    }

}
