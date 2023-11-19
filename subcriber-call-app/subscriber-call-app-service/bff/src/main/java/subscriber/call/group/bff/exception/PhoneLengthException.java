package subscriber.call.group.bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneLengthException extends RuntimeException {

    public static final String PHONE_NUMBER_LENGTH_EXCEPTION_TEXT = "Phone number %d  has length more than 6";

    public PhoneLengthException() {
        super(String.format(PHONE_NUMBER_LENGTH_EXCEPTION_TEXT));
    }
}
