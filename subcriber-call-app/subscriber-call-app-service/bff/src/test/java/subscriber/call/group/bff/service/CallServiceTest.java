package subscriber.call.group.bff.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.bff.TestUtil;
import subscriber.call.group.bff.dto.CallDto;
import subscriber.call.group.bff.dto.CallResponseDto;
import subscriber.call.group.bff.service.rabbit.FinishCallRabbitSender;
import subscriber.call.group.bff.service.rabbit.StartCallRabbitSender;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CallServiceTest {

    @MockBean(name="startCallRabbitSender")
    StartCallRabbitSender startCallRabbitSender;

    @MockBean(name="finishCallRabbitSender")
    FinishCallRabbitSender finishCallRabbitSender;

    @Autowired
    private CallService testObj;


    @BeforeEach
    void setUp() throws Exception {
        when(startCallRabbitSender.sendAndReceive(any(CallDto.class))).thenReturn(new CallResponseDto("STARTED", "started message"));
        when(finishCallRabbitSender.sendAndReceive(any(CallDto.class))).thenReturn(new CallResponseDto("FINiSHED", "finished message"));
    }

    @Test
    void startCall_whenCallShouldBeStarted_thenReturnStartedReply() {
        var callResponseDto = testObj.startCall(123456l, 234567l);
        assertNotNull(callResponseDto);
        assertEquals("STARTED", callResponseDto.getStatus());
        assertEquals("started message", callResponseDto.getMessage());
    }


    @Test
    void finishCall_whenCallShouldBeFinished_thenReturnFinishedReply() {
        var callResponseDto = testObj.finishCall(123456l, 234567l);
        assertNotNull(callResponseDto);
        assertEquals("FINiSHED", callResponseDto.getStatus());
        assertEquals("finished message", callResponseDto.getMessage());
    }




}
