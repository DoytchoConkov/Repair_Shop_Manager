package mainPackage.web;

import mainPackage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String home(ModelAndView modelAndView) {
        return "/home";
    }
}
