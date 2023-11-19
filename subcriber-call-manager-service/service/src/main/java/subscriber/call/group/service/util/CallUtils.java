package subscriber.call.group.service.util;

import subscriber.call.group.service.domain.Status;
import subscriber.call.group.service.dto.CallResponseDto;

public class CallUtils {

    public static boolean validatePhone(Long phone) {
        return phone != null && String.valueOf(phone.longValue()).length() <= 6;
    }

    public static CallResponseDto validatePhones (Long initPhone, Long receivingPhone) {
        if (validatePhone(initPhone) || validatePhone(receivingPhone)){
            var errorResponse = new CallResponseDto();
            errorResponse.setStatus(Status.ERROR);
            errorResponse.setMessage("One Number is not valid");
            return errorResponse;
        }

        return null;

    }
}
