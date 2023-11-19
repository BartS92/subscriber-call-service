package subscriber.call.group.service.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class CallDto implements Serializable {
    private long initPhone;
    private long receivingPhone;
}