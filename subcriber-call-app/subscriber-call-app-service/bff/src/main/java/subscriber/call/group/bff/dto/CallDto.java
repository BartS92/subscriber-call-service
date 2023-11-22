package subscriber.call.group.bff.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import subscriber.call.group.bff.domain.ConversationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallDto implements Serializable {
    private long initPhone;
    private long receivingPhone;
}
