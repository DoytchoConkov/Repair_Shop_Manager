package mainPackage.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static mainPackage.constants.Constants.SPARE_PART_NOT_FOUND;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = SPARE_PART_NOT_FOUND)
public class SparePartIdNotFoundException extends RuntimeException {
    public SparePartIdNotFoundException(String message) {
        super(message);
    }
}
