package mainPackage.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No spare part with this Id")
public class SparePartIdNotFoundException extends RuntimeException{
    public SparePartIdNotFoundException() {
    }

    public SparePartIdNotFoundException(String message) {
        super(message);
    }
}
