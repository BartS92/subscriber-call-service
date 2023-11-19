package subscriber.call.group.bff.dto.stats;

import java.util.List;
import lombok.Data;

@Data
public class StatsDto {
    private List<CallInfoDto> callInfo;

    private long totalCount;
    private long failedCount;
    private long totalTime;

    private CallCountDto popularPhonesByCallCount;
    private CallCountDto popularPhonesByCallDuration;
    private CallCountDto popularPhoneByTotalTime;
}
