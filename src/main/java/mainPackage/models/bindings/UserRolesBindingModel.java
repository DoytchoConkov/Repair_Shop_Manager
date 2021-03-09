package mainPackage.models.bindings;

import java.util.List;

public class UserRolesBindingModel {
    private String username;
    private List<String> roles;

    public UserRolesBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
