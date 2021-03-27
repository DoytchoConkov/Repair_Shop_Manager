package mainPackage.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No order with this Id")
public class OrderIdNotFoundException extends RuntimeException {
    public OrderIdNotFoundException(String message) {
        super(message);
    }
}
