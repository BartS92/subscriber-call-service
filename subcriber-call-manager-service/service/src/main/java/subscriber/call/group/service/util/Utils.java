package subscriber.call.group.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Date;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Date getCurrentTimestamp() {
        var input = new Date();
        Instant instant = input.toInstant();
        return Date.from(instant);
    }

    public static String convertToJson(Object object) {
        var mapper = objectMapper.writer().withDefaultPrettyPrinter();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return "";
        }
    }
}
