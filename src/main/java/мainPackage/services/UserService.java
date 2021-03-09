package мainPackage.services;

import мainPackage.models.bindings.UserRolesBindingModel;
import мainPackage.models.entities.User;
import мainPackage.models.services.UserServiceModel;
import мainPackage.models.views.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
     void registerUserAndLogin(UserServiceModel userServiceModel);
    UserServiceModel findUserByUserName(String username);

    User getUserByUserName(String springSecurityFormUsernameKey);

    List<UserViewModel> getUsers();

    void setRoles(UserRolesBindingModel userRolesBindingModel);
}
