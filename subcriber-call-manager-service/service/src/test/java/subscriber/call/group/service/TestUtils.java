package subscriber.call.group.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallResponseDto;

public class TestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static CallResponseDto convertToCallResponse(String response) {
        try{
            return mapper.readValue(response, CallResponseDto.class);
        }
        catch (JsonProcessingException e){
            var error = new CallResponseDto(Status.ERROR, "Something wrong during conversion");
            return error;
        }
    }
}
