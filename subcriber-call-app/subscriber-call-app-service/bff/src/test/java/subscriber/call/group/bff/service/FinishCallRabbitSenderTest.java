package subscriber.call.group.bff.service;

import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.bff.service.rabbit.FinishCallRabbitSender;

@SpringBootTest
@ActiveProfiles("test")
public class FinishCallRabbitSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private FinishCallRabbitSender testObj;




}
