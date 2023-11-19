package subscriber.call.group.service.service.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallDto;
import subscriber.call.group.service.dto.CallResponseDto;
import subscriber.call.group.service.service.EventService;
import subscriber.call.group.service.service.SubscriberService;
import subscriber.call.group.service.util.CallUtils;
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

        var result = CallUtils.validatePhones(initPhone, receivingPhone);
        if (result != null){
            Utils.convertToJson(result);
        }

        Status status;
        String msg;

        var receiving = subscriberService.getSubscriber(receivingPhone);
        if (receiving == null) {
            status = Status.NOT_FOUND;
            msg = String.format("%d number is not found", receivingPhone);
        }
        else {
            var receivingPhoneEvent = eventService.getLastPhoneEvent(receivingPhone);
            if ((receivingPhoneEvent != null && Status.valueOf(receivingPhoneEvent.getStatus()) == Status.STARTED) || initPhone.equals(receivingPhone)) {
                status = Status.BUSY;
                msg = String.format("%d number is busy", receivingPhone);
            } else {
                status = Status.STARTED;
                msg = String.format("Call between %d and %d has been started",initPhone, receivingPhone);
            }
        }

        eventService.createAndSave(initPhone, receivingPhone, status);

        var response = new CallResponseDto();
        response.setStatus(status);
        response.setMessage(msg);

        return Utils.convertToJson(response);
    }
}
