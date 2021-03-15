package mainPackage.web;

import org.springframework.boot.web.servlet.error.ErrorController;

public class RSMErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return null;
    }
}
