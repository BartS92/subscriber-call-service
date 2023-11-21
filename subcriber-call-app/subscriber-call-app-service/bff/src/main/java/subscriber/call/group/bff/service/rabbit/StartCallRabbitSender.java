package subscriber.call.group.bff.service.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import subscriber.call.group.bff.dto.CallDto;
import subscriber.call.group.bff.dto.CallResponseDto;
import subscriber.call.group.bff.util.Utils;

@Slf4j
@Service("startCallRabbitSender")
@RequiredArgsConstructor
public class StartCallRabbitSender {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq-settings.exchange}")
    private String exchange;


    @Value("${rabbitmq-settings.routing-keys.start-call}")
    private String routingKey;


    public CallResponseDto sendAndReceive(CallDto dto) {
        var response = amqpTemplate.convertSendAndReceive(exchange, routingKey, dto);
        return  Utils.convertToCallResponse((String) response);
    }
}
