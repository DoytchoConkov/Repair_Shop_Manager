package mainPackage.services.impl;

import mainPackage.models.bindings.UserRolesBindingModel;
import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.models.services.UserServiceModel;
import mainPackage.repositories.UserRepository;
import mainPackage.repositories.UserRoleRepository;
import mainPackage.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceDB userServiceDB;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private UserRoleRepository mockRoleRepository;

    @Test
    void registerUserAndLogin() throws IOException {
        User user = new User();
        UserServiceModel userModel = new UserServiceModel();
        user.setUsername("Ivan");
        user.setPassword("12345");
        userModel.setUsername("Ivan");
        userModel.setPassword("12345");
        UserRole userRole = new UserRole();
        userRole.setRole(RoleName.ADMIN);
        Mockito.when(mockRoleRepository.findByRole(RoleName.ADMIN)).thenReturn(Optional.of(userRole));
        Mockito.when(mockRoleRepository.count()).thenReturn(1L);
        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(Optional.of(user));
        userService.registerUserAndLogin(userModel);
    }

    @Test
    void findUserByUserName() {
        User user = new User();
        user.setUsername("Ivan");
        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(java.util.Optional.of(user));
        assertFalse(userService.findUserByUserName("Ivan"));
    }

    @Test
    void findUserByUserNameThrowError() {

//        Exception exception = assertThrows(NumberFormatException.class, () -> {
//            Integer.parseInt("1a");
//        });
//        String expectedMessage = "For input string";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//        TO:
//        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(Optional.empty());
//        assertFalse(userService.findUserByUserName("Ivan"));
    }

    @Test
    void findUserByUserNameReturnFalse() {
        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(Optional.empty());
        assertTrue(userService.findUserByUserName("Ivan"));
    }

    @Test
    void getUserByUserName() {
        User user = new User();
        user.setUsername("Ivan");
        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(java.util.Optional.of(user));
        assertEquals(user, userService.getUserByUserName("Ivan"));
    }

    @Test
    void getUsers() {
        List<User> users = List.of(new User());
        Mockito.when(mockUserRepository.findAll()).thenReturn(users);
        assertEquals(users.size(), userService.getUsers().size());
    }

    @Test
    void setRoles() {
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setRole(RoleName.ADMIN);
        user.setUsername("Ivan");
        user.setPassword("12345");
        user.getRoles().add(userRole);
        UserRolesBindingModel userRolesBindingModel = new UserRolesBindingModel();
        userRolesBindingModel.setUsername("Ivan");
        userRolesBindingModel.setRoles(List.of("ADMIN"));
        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(java.util.Optional.of(user));
        Mockito.when(mockRoleRepository.findByRole(RoleName.ADMIN)).thenReturn(Optional.of(userRole));
        userService.setRoles(userRolesBindingModel);
    }

    @Test
    void isMoreOneAdmin() {
        Mockito.when(mockUserRepository.findAdminUsers()).thenReturn(1);
        assertTrue(userService.isMoreOneAdmin());
    }

    @Test
    void isMoreOneAdmin2() {
        Mockito.when(mockUserRepository.findAdminUsers()).thenReturn(2);
        assertFalse(userService.isMoreOneAdmin());
    }
}