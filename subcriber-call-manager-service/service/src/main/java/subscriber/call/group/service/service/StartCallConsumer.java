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
public class StartCallConsumer {

    @Autowired
    private EventService eventService;

    @Autowired
    private SubscriberService subscriberService;

    @RabbitListener(queues = "${rabbitmq-settings.queues.start-call}", concurrency = "3")
    public String receive(CallDto dto)  {
        var initPhone = dto.getInitPhone();
        var receivingPhone = dto.getReceivingPhone();

        Status status;
        String msg;

        var receiving = subscriberService.getSubscriber(dto.getReceivingPhone());
        if (receiving == null) {
            status = Status.NOT_FOUND;
            msg = String.format("%d number is not found", dto.getReceivingPhone());
        }
        else {
            var receivingPhoneEvent = eventService.getLastPhoneEvent(dto.getReceivingPhone());
            if (receivingPhoneEvent!= null && Status.valueOf(receivingPhoneEvent.getStatus()) == Status.STARTED){
                status = Status.BUSY;
                msg = String.format("%d number is busy", dto.getReceivingPhone());
            }
            else {
                status = Status.STARTED;
                msg = String.format("Call between %d and %d has been started", dto.getInitPhone(), dto.getReceivingPhone());
            }
        }

        eventService.createAndSave(initPhone, receivingPhone, status);

        var response = new CallResponseDto();
        response.setStatus(status);
        response.setMessage(msg);

        return Utils.convertToJson(response);
    }
}
