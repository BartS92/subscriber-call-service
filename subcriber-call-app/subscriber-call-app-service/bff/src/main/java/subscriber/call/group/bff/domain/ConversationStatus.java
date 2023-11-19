package subscriber.call.group.bff.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConversationStatus {
    START("START"),
    FINISH("FINISH");

    private String value;
}
