package subscriber.call.group.bff.config.stats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class StatsConfig {
    @Bean(name = "statsWebClient")
    public WebClient getWebClient(StatsProperties apiProperties) {
        return  WebClient.builder().baseUrl(apiProperties.getBaseUrl()).build();
    }
}
