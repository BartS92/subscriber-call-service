package subscriber.call.group.service.service.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallDto;
import subscriber.call.group.service.dto.CallResponseDto;
import subscriber.call.group.service.service.EventService;
import subscriber.call.group.service.util.CallUtils;
import subscriber.call.group.service.util.Utils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinishCallConsumer {

    private final EventService eventService;

    @RabbitListener(queues = "${rabbitmq-settings.queues.finish-call}", concurrency = "3")
    public String receiveAndReply(CallDto dto) {
        var result = CallUtils.validatePhones(dto.getInitPhone(), dto.getReceivingPhone());
        if (result != null){
            Utils.convertToJson(result);
        }

        var event = eventService.getLastCallEvent(dto.getInitPhone(), dto.getReceivingPhone());
        var response = new CallResponseDto();
        if (event != null) {
            if (Status.valueOf(event.getStatus()) == Status.STARTED) {
                var status = Status.FINISHED;
                eventService.createAndSave(dto.getInitPhone(), dto.getReceivingPhone(), status);
                response.setStatus(status);
                response.setMessage(String.format("Call between  %d and %d has been finished ", dto.getInitPhone(), dto.getReceivingPhone()));
            } else {
                response.setStatus(Status.valueOf(event.getStatus()));
                response.setMessage(String.format("Last call event for %d and %d with %s status ", dto.getInitPhone(), dto.getReceivingPhone(), event.getStatus()));
            }
        } else {
            response.setStatus(Status.NO_STATUS);
            response.setMessage(String.format("No active calls between %d and %d", dto.getInitPhone(), dto.getReceivingPhone()));
        }

        return Utils.convertToJson(response);
    }
}
