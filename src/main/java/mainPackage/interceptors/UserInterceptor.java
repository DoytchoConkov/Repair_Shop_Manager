package mainPackage.interceptors;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.UserRole;
import mainPackage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
public class UserInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public UserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        modelAndView.addObject("haveOnlyUser", false);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!"anonymousUser".equals(principal)) {
            String username = ((UserDetails) principal).getUsername();
            Set<UserRole> roles = userService.getUserByUserName(username).getRoles();
            if (roles.size() == 0 || (roles.size() == 1 && roles.contains(RoleName.USER))) {
                modelAndView.addObject("haveOnlyUser", true);
            }
        }
    }
}
