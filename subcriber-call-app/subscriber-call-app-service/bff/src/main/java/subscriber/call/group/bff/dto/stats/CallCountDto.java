package subscriber.call.group.bff.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallCountDto {
    long phone;
    long count;
}
