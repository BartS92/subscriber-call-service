package subscriber.call.group.service.dto;

import java.io.Serializable;
import lombok.Data;
import subscriber.call.group.service.domain.Status;


@Data
public class CallResponseDto implements Serializable {
    private Status status;
    private String message;

    public CallResponseDto(){}

    public CallResponseDto(Status status, String message){
        this.status = status;
        this.message = message;
    }
}
