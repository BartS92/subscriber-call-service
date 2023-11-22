package subscriber.call.group.service.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import subscriber.call.group.service.domain.Status;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallResponseDto implements Serializable {
    private Status status;
    private String message;
}
