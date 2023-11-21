package subscriber.call.group.bff.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import subscriber.call.group.bff.domain.ConversationStatus;

@Data
@AllArgsConstructor
public class CallDto implements Serializable {
    private long initPhone;
    private long receivingPhone;
}
