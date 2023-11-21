package subscriber.call.group.bff.service;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import subscriber.call.group.bff.service.rabbit.FinishCallRabbitSender;
import subscriber.call.group.bff.service.rabbit.StartCallRabbitSender;

@SpringBootTest
@ActiveProfiles("test")
class CallServiceTest {

    @Mock
    StartCallRabbitSender startCallRabbitSender;

    @Mock
    FinishCallRabbitSender finishCallRabbitSender;


}
