package subscriber.call.group.bff.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import subscriber.call.group.bff.dto.CallResponseDto;

public class Utils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static CallResponseDto convertToCallResponse(String response) {
        try{
            return mapper.readValue(response, CallResponseDto.class);
        }
        catch (JsonProcessingException e){
            var error = new CallResponseDto();
            error.setMessage("Something wrong during conversion");
            error.setStatus("ERROR");
            return error;
        }
    }
}
