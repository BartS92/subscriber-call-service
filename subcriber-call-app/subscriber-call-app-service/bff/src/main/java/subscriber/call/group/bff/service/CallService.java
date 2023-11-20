package subscriber.call.group.bff.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import subscriber.call.group.bff.dto.CallDto;
import subscriber.call.group.bff.dto.CallResponseDto;
import subscriber.call.group.bff.dto.EventResponseDto;
import subscriber.call.group.bff.dto.stats.StatsDto;
import subscriber.call.group.bff.service.rabbit.FinishCallRabbitSender;
import subscriber.call.group.bff.service.rabbit.StartCallRabbitSender;
import subscriber.call.group.bff.util.StatsUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallService {

    private final StartCallRabbitSender startCallRabbitSender;

    private final FinishCallRabbitSender finishCallRabbitSender;

    private final WebClient statsWebClient;

    public CallResponseDto startCall(long initPhone, long receivingPhone) throws JsonProcessingException {
        return startCallRabbitSender.sendAndReceive(new CallDto(initPhone, receivingPhone));
    }

    public CallResponseDto finishCall(long initPhone, long receivingPhone) throws JsonProcessingException {
        return finishCallRabbitSender.sendAndReceive(new CallDto(initPhone, receivingPhone));
    }

    public StatsDto getStats() {
        var events = statsWebClient
                .get()
                .uri("/stats")
                .retrieve()
                .bodyToMono(EventResponseDto[].class)
                .block();

        Arrays.sort(events);

        return StatsUtils.createStats(events);
    }
}
