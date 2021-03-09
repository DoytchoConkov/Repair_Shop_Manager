package мainPackage.web;

import мainPackage.models.bindings.UserRolesBindingModel;
import мainPackage.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminControler {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminControler(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/set-user-role")
    @PreAuthorize("isAuthenticated()")
    public String addUserRole(Model model) {
        model.addAttribute("users", userService.getUsers());
        if (!model.containsAttribute("userRolesBindingModel")) {
            model.addAttribute("userRolesBindingModel", new UserRolesBindingModel());
        }
        return "/extended/add-or-edit-user-role";
    }

    @PostMapping("/set-user-role")
    @PreAuthorize("isAuthenticated()")
    public String addUserRoleConfirm(@Valid UserRolesBindingModel userRolesBindingModel, RedirectAttributes redirectAttributes) {
        if (userRolesBindingModel.getRoles().isEmpty() || userRolesBindingModel.getUsername().isEmpty()) {
            redirectAttributes.addFlashAttribute("userRolesBindingModel", userRolesBindingModel);
            redirectAttributes.addFlashAttribute("incorect", true);
            return "redirect:/admin/set-user-role";
        }
        userService.setRoles(userRolesBindingModel);
        return "/index";
    }
}
