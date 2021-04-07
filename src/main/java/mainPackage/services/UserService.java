package mainPackage.services;

import mainPackage.models.bindings.UserRolesBindingModel;
import mainPackage.models.entities.User;
import mainPackage.models.services.UserServiceModel;
import mainPackage.models.views.UserViewModel;

import java.io.IOException;
import java.util.List;

public interface UserService{
    void registerUserAndLogin(UserServiceModel userServiceModel) throws IOException;

    boolean findUserByUserName(String username);

    User getUserByUserName(String springSecurityFormUsernameKey);

    List<UserViewModel> getUsers();

    void setRoles(UserRolesBindingModel userRolesBindingModel);

    boolean isMoreOneAdmin();

}
