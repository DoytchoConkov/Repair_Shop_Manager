package mainPackage.models.bindings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {
    private String username;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotBlank(message = "Username can not be empty.")
    @Size(min = 3, max = 20, message = "Username length must be between three and  twenty characters.")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Password can not be empty.")
    @Size(min = 4, max = 20, message = "Password length must be between four and  twenty characters.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
