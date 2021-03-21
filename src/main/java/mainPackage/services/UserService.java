package mainPackage.services;

import mainPackage.models.bindings.UserRolesBindingModel;
import mainPackage.models.entities.User;
import mainPackage.models.services.UserServiceModel;
import mainPackage.models.views.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {
    void registerUserAndLogin(UserServiceModel userServiceModel) throws IOException;
     
    UserServiceModel findUserByUserName(String username);

    User getUserByUserName(String springSecurityFormUsernameKey);

    List<UserViewModel> getUsers();

    void setRoles(UserRolesBindingModel userRolesBindingModel);

    boolean isMoreOneAdmin();
}
