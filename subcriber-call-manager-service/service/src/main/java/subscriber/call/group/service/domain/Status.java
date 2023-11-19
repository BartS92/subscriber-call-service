package subscriber.call.group.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    NOT_FOUND("NOT_FOUND"),
    STARTED("STARTED"),
    FINISHED("FINISHED"),
    BUSY("BUSY");

    private String value;
}
