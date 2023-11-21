package subscriber.call.group.service.util;

import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallResponseDto;
import subscriber.call.group.service.entity.Event;

public class CallUtils {

    public static boolean validatePhone(Long phone) {
        return phone != null && String.valueOf(phone.longValue()).length() <= 6;
    }

    public static CallResponseDto validatePhones (Long initPhone, Long receivingPhone) {
        if (!validatePhone(initPhone) && !validatePhone(receivingPhone)){
            var errorResponse = new CallResponseDto();
            errorResponse.setStatus(Status.ERROR);
            errorResponse.setMessage("One Number is not valid");
            return errorResponse;
        }

        return null;

    }

    public static String createBusyResponse (long phone) {
        var response =  new CallResponseDto();
        response.setStatus(Status.BUSY);
        response.setMessage(String.format("%d number is busy", phone));
        return Utils.convertToJson(response);
    }

    public static String createAlreadyStartedResponse (long initPhone, long receivingPhone) {
        var response =  new CallResponseDto();
        response.setStatus(Status.STARTED);
        response.setMessage(String.format("the call between %d and %d already started", initPhone, receivingPhone));
        return Utils.convertToJson(response);
    }
}
