package subscriber.call.group.bff.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import subscriber.call.group.bff.TestUtil;
import subscriber.call.group.bff.dto.EventResponseDto;
import subscriber.call.group.bff.dto.stats.CallCountDto;
import subscriber.call.group.bff.dto.stats.CallInfoDto;
import subscriber.call.group.bff.dto.stats.TimeDataDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class StatsServiceTest {
    public static String VALID_JSON_RESPONSE = "statsResponse/stats";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean(name = "statsWebClient")
    private WebClient webClient;

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

        var validSearchResponse = TestUtil.readFile(getClass(), VALID_JSON_RESPONSE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var statsResponse = objectMapper.readValue(validSearchResponse, EventResponseDto[].class);


        // stub web client
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/stats")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(EventResponseDto[].class)).thenReturn(Mono.just(statsResponse));
    }

    @Test
    void getStats_whenGiveValidRequest_thenReturnStatsDto() {
        var statsDto = testObj.getStats();
        assertNotNull(statsDto);

        assertEquals(1, statsDto.getTotalCount());
        assertEquals(0, statsDto.getFailedCount());
        assertEquals(29, statsDto.getTotalTime());
        assertEquals(new CallCountDto(234567, 29), statsDto.getPopularPhoneByTotalTime());
        assertEquals(new CallCountDto(234567, 1), statsDto.getPopularPhonesByCallCount());
        assertEquals(new CallCountDto(234567, 29), statsDto.getPopularPhonesByCallDuration());

        assertEquals(new ArrayList<CallInfoDto>(){
            {
                add(new CallInfoDto(539390, new HashMap<Long, TimeDataDto>(){{put(234567l, new TimeDataDto(29, 0));}}));
                add(new CallInfoDto(234567, new HashMap<Long, TimeDataDto>(){{put(539390l, new TimeDataDto(0, 29));}}));

            }
        }, statsDto.getCallInfo());
    }
}
