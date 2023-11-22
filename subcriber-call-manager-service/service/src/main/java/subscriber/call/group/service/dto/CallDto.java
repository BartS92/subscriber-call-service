package subscriber.call.group.service.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallDto implements Serializable {
    private Long initPhone;
    private Long receivingPhone;
}
