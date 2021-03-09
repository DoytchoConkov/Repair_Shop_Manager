package mainPackage.web;

import org.springframework.boot.web.servlet.error.ErrorController;

public class RSMErrorControler implements ErrorController {
    @Override
    public String getErrorPath() {
        return null;
    }
}
