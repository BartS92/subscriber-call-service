package subscriber.call.group.bff.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import subscriber.call.group.bff.dto.CallDto;
import subscriber.call.group.bff.dto.CallResponseDto;
import subscriber.call.group.bff.service.rabbit.FinishCallRabbitSender;
import subscriber.call.group.bff.service.rabbit.StartCallRabbitSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallService {

    private final StartCallRabbitSender startCallRabbitSender;

    private final FinishCallRabbitSender finishCallRabbitSender;

    public CallResponseDto startCall(long initPhone, long receivingPhone) {
        return startCallRabbitSender.sendAndReceive(new CallDto(initPhone, receivingPhone));
    }

    public CallResponseDto finishCall(long initPhone, long receivingPhone) {
        return finishCallRabbitSender.sendAndReceive(new CallDto(initPhone, receivingPhone));
    }
}
