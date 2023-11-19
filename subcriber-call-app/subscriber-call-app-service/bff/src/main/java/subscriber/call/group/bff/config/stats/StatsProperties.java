package subscriber.call.group.bff.config.stats;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "subscriber-call-manager-service")
public class StatsProperties {

    private String host;
    private String path;

    public String getBaseUrl() {
        return this.host + this.path;
    }
}
