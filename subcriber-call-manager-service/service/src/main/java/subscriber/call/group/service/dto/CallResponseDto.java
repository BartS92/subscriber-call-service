package subscriber.call.group.service.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import subscriber.call.group.service.domain.Status;


@Data
@RequiredArgsConstructor
public class CallResponseDto implements Serializable {
    private Status status;
    private String message;
}
