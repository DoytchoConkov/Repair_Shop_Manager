package mainPackage.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Username not exist")
public class UsernamesNotFoundException extends RuntimeException {
    public UsernamesNotFoundException(String message) {
        super(message);
    }
}
