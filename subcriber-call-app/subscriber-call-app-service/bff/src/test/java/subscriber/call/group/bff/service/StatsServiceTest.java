package subscriber.call.group.bff.service;

import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import subscriber.call.group.bff.dto.EventResponseDto;
import subscriber.call.group.bff.dto.stats.StatsDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class StatsServiceTest {

    @Mock
    private WebClient webClientMock;

    private EventResponseDto[] statsResponse;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @Autowired
    private StatsService testObj;

    @BeforeEach
    void setUp() throws Exception {
        statsResponse = new EventResponseDto[2];
        var stats1 = new EventResponseDto();
        var stats2 = new EventResponseDto();

        statsResponse[0] = stats1;
        statsResponse[1] = stats2;


        // stub web client
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(any(Function.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(EventResponseDto[].class)).thenReturn(Mono.just(statsResponse));
    }

    @Test
    void getStats_whenGiveValidRequest_thenReturnStatsDto() {
        var statsDto = testObj.getStats();

        assertNotNull(statsDto);
    }
}
