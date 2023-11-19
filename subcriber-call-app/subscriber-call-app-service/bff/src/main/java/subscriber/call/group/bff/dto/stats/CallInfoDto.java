package subscriber.call.group.bff.dto.stats;

import java.util.Map;
import lombok.Data;

@Data
public class CallInfoDto {
    private long initPhone;
    private Map<Long, TimeDataDto> info;
}
