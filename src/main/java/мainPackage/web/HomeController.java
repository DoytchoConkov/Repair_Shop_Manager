package мainPackage.web;

import мainPackage.services.UserService;
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
    @PreAuthorize("isAnonymous()")
    public String index() {
        return "/index";
    }

    @GetMapping("/index")
    @PreAuthorize("isAuthenticated()")
    public String home(ModelAndView modelAndView) {
        boolean areFrontOffice = true;
        boolean areBackOffice = true;

        modelAndView.addObject("areFrontOffice", areFrontOffice);
        modelAndView.addObject("areBackOffice", areBackOffice);
        return "/index";
    }
}
