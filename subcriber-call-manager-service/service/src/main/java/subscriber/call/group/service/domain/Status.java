package subscriber.call.group.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    NOT_FOUND("NOT_FOUND"),
    STARTED("STARTED"),
    FINISHED("FINISHED"),
    BUSY("BUSY"),
    ERROR("ERROR"),
    NO_STATUS("NO_STATUS");

    private String value;
}
