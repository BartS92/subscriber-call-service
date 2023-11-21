package subscriber.call.group.service.dto;

import java.io.Serializable;
import lombok.Data;


@Data
public class CallDto implements Serializable {
    private Long initPhone;
    private Long receivingPhone;
}
