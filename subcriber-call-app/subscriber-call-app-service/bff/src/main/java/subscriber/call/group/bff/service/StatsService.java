package subscriber.call.group.bff.service;


import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;;
import subscriber.call.group.bff.dto.EventResponseDto;
import subscriber.call.group.bff.dto.stats.StatsDto;
import subscriber.call.group.bff.util.StatsUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsService {
    private final WebClient statsWebClient;


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
