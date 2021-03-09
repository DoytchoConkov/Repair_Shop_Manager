package mainPackage.web;

import mainPackage.models.bindings.UserRegisterBindingModel;
import mainPackage.models.services.UserServiceModel;
import mainPackage.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserControler {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserControler(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

//    @ModelAttribute("registrationBindingModel")
//    public UserRegistrationBindingModel createBindingModel() {
//        return new UserRegistrationBindingModel();
//    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "/user/auth-register";
    }


    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid @ModelAttribute UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }
        if (userService.findUserByUserName(userRegisterBindingModel.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("userExist", true);
            return "redirect:/users/register";
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("notEqual", true);
            return "redirect:/users/register";
        }

        this.userService.registerUserAndLogin(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:/index";
    }

    @GetMapping("/login")
//   @PreAuthorize("isAnonymous()")
    public String login() {
        return "/user/auth-login";
    }


    @PostMapping("/login-error")
    public ModelAndView failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                            String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bad_credentials", true);
        modelAndView.addObject("username", username);
        modelAndView.setViewName("/user/auth-login");
        // TODO:
        return modelAndView;
    }

}
