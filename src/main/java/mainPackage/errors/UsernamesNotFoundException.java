package mainPackage.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static mainPackage.constants.Constants.USERNAME_NOT_FOUND;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = USERNAME_NOT_FOUND)
public class UsernamesNotFoundException extends RuntimeException {
    public UsernamesNotFoundException(String message) {
        super(message);
    }
}
