package mainPackage.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static mainPackage.constants.Constants.ORDER_NOT_FOUND;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ORDER_NOT_FOUND)
public class OrderIdNotFoundException extends RuntimeException {
    public OrderIdNotFoundException(String message) {
        super(message);
    }
}
