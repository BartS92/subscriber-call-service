package subscriber.call.group.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallDto;
import subscriber.call.group.service.dto.CallResponseDto;
import subscriber.call.group.service.util.Utils;

@Slf4j
@Service
public class FinishCallConsumer {

    @Autowired
    private EventService eventService;


    @RabbitListener(queues = "${rabbitmq-settings.queues.finish-call}", concurrency = "3")
    public String receive(CallDto dto) {
        var event = eventService.getLastPhoneEvent(dto.getInitPhone());
        var response = new CallResponseDto();
        if (event != null) {
            if (event.getReceivingPhone() == dto.getReceivingPhone() && Status.valueOf(event.getStatus()) == Status.STARTED) {
                var status = Status.FINISHED;
                eventService.createAndSave(dto.getInitPhone(), dto.getReceivingPhone(), status);
                response.setStatus(status);
                response.setMessage(String.format("Calls between  %d and %d has been finished ", dto.getInitPhone(), dto.getReceivingPhone()));
            } else {
                response.setStatus(Status.valueOf(event.getStatus()));
                response.setMessage(String.format("Last event for %d: %d and %d with %s status ", dto.getInitPhone(), dto.getInitPhone(), dto.getReceivingPhone(), event.getStatus()));
            }
        }
        else {
//            response.setStatus(status);
            response.setMessage(String.format("Calls between %d and %d has been finished", dto.getInitPhone(), dto.getReceivingPhone()));
        }



        return Utils.convertToJson(response);
    }
}
