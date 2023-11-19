package subscriber.call.group.bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneIsNullException extends RuntimeException {

    public static final String PHONE_NUMBER_IS_NULL_TEXT = "Phone number can not be null";

    public PhoneIsNullException() {
        super(String.format(PHONE_NUMBER_IS_NULL_TEXT));
    }
}
