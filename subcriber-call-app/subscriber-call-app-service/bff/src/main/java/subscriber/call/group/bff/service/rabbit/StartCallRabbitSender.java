package subscriber.call.group.bff.service.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import subscriber.call.group.bff.dto.CallDto;
import subscriber.call.group.bff.dto.CallResponseDto;

@Slf4j
@Service("startCallRabbitSender")
@Data
public class StartCallRabbitSender {

    @Autowired
    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq-settings.exchange}")
    private String exchange;


    @Value("${rabbitmq-settings.routing-keys.start-call}")
    private String routingKey;


    public CallResponseDto sendAndReceive(CallDto dto) throws JsonProcessingException {
        var response = amqpTemplate.convertSendAndReceive(exchange, routingKey, dto);
        var mapper = new ObjectMapper();
        var responseDto = mapper.readValue((String)response, CallResponseDto.class);

        return responseDto;
    }
}
