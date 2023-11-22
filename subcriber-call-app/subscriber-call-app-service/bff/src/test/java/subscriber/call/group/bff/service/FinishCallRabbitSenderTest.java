package subscriber.call.group.bff.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.bff.TestUtil;
import subscriber.call.group.bff.dto.CallDto;
import subscriber.call.group.bff.dto.CallResponseDto;
import subscriber.call.group.bff.service.rabbit.FinishCallRabbitSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class FinishCallRabbitSenderTest {

    @MockBean(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FinishCallRabbitSender testObj;

    @BeforeEach
    void setUp() throws Exception {
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString(), any(CallDto.class))).thenReturn(TestUtil.convertToJson(new CallResponseDto("STARTED", "call has been started")));
    }

    @Test
    void sendAndReceive_whenFinishRequestSent_thenReturnFinishedResponse() {
        var response = testObj.sendAndReceive(new CallDto(1234, 12346));

        assertEquals("STARTED" ,response.getStatus());
        assertEquals("call has been started" ,response.getMessage());
    }

}
