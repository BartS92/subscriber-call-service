package subscriber.call.group.bff.dto.stats;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallInfoDto {
    private long initPhone;
    private Map<Long, TimeDataDto> info;
}
