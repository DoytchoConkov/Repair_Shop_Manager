package mainPackage.models.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Set;

public class UserServiceModel {
    private String id;
    private String username;
    private MultipartFile imageUrl;
    private String password;
    private Set<RoleServiceModel> authorities;

    public UserServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile  getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile  imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
