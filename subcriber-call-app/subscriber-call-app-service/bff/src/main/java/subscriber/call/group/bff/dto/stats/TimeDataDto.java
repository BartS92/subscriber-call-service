package subscriber.call.group.bff.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeDataDto {
    private long incomingTime;
    private long outgoingTime;
}
