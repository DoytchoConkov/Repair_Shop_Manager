package mainPackage.services;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository mockUserRepository;

    @Test
    void registerUserAndLogin() {
        //TODO:
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
        assertEquals(user,userService.getUserByUserName("Ivan"));
    }

    @Test
    void getUsers() {
        List<User> users = List.of(new User());
        Mockito.when(mockUserRepository.findAll()).thenReturn(users);
        assertEquals(users.size(),userService.getUsers().size());
    }

    @Test
    void setRoles() {
        //TODO:
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