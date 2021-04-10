package mainPackage.web;

import mainPackage.models.bindings.UserRolesBindingModel;
import mainPackage.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public AdminControler(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/set-user-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUserRole(Model model) {
        model.addAttribute("users", userService.getUsers());
        if (!model.containsAttribute("userRolesBindingModel")) {
            model.addAttribute("userRolesBindingModel", new UserRolesBindingModel());
        }
        return "/user/add-or-edit-user-role";
    }

    @PostMapping("/set-user-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUserRoleConfirm(@Valid UserRolesBindingModel userRolesBindingModel, RedirectAttributes redirectAttributes) {
        if (userRolesBindingModel.getUsername().isEmpty()) {
            redirectAttributes.addFlashAttribute("userRolesBindingModel", userRolesBindingModel);
            redirectAttributes.addFlashAttribute("incorrect", true);
            return "redirect:/admin/set-user-role";
        }
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
         if(userRolesBindingModel.getUsername().equals(username)&&userService.isMoreOneAdmin()&&!userRolesBindingModel.getRoles().contains("ADMIN")){
             redirectAttributes.addFlashAttribute("userRolesBindingModel", userRolesBindingModel);
             redirectAttributes.addFlashAttribute("minOneAdmin", true);
             return "redirect:/admin/set-user-role";
         }
        userService.setRoles(userRolesBindingModel);
        return "/home";
    }
}
