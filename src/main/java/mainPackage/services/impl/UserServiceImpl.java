package mainPackage.services.impl;

import mainPackage.errors.UsernamesNotFoundException;
import mainPackage.models.bindings.UserRolesBindingModel;
import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.models.services.UserServiceModel;
import mainPackage.models.views.UserViewModel;
import mainPackage.repositories.UserRepository;
import mainPackage.repositories.UserRoleRepository;
import mainPackage.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserServiceDB userServiceDB;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceDB userServiceDB) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userServiceDB = userServiceDB;
    }

    @Override
    public void registerUserAndLogin(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        if (userRepository.count() == 0) {
            UserRole userRole = userRoleRepository.
                    findByRole(RoleName.ADMIN).orElseThrow(() ->
                    new IllegalStateException("USER role not found. Please seed the roles."));

            user.getRoles().add(userRole);
        }
        this.userRepository.save(user);
        UserDetails principal = userServiceDB.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean findUserByUserName(String username) {
        return this.userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public User getUserByUserName(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() ->
                new UsernamesNotFoundException(String.format("%s not exist.", username)));
    }

    @Override
    public List<UserViewModel> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> modelMapper.map(u, UserViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void setRoles(UserRolesBindingModel userRolesBindingModel) {
        User user = userRepository.findByUsername(userRolesBindingModel.getUsername()).orElseThrow();
        user.getRoles().clear();
        userRolesBindingModel.getRoles().forEach(r -> {
            UserRole userRole = userRoleRepository.
                    findByRole(RoleName.valueOf(r)).orElseThrow(
                    () -> new IllegalStateException("User Role not found. Please seed the roles."));
            user.getRoles().add(userRole);
        });
        userRepository.save(user);
    }

    @Override
    public boolean isMoreOneAdmin() {
        int admins = userRepository.findAdminUsers();
        return admins == 1;
    }

}
