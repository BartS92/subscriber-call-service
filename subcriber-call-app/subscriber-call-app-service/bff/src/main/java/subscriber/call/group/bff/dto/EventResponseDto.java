package subscriber.call.group.bff.dto;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EventResponseDto implements Comparable<EventResponseDto> {
    private long id;

    private String status;

    private long initPhone;

    private long receivingPhone;

    private Date dateCreated;

    @Override
    public int compareTo(EventResponseDto o) {
        int comp = Long.compare(getInitPhone(), o.getInitPhone());
        if (comp != 0){
            return comp;
        }

        comp = Long.compare(getReceivingPhone(), o.getReceivingPhone());
        if (comp != 0) {
            return comp;
        }

        return getDateCreated().compareTo(o.getDateCreated());
    }
}
