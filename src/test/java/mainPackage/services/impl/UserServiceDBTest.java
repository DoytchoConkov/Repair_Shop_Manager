package mainPackage.services.impl;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceDBTest {

    private UserServiceDB serviceToTest;
    @MockBean
    private UserRepository mockUserRepository;
    @BeforeEach
    public void setUp() {
        serviceToTest = new UserServiceDB(mockUserRepository);
    }
    @Test
    void testExistingUser() {
        User user = new User();
        user.setUsername("Gosho");
        user.setPassword("12345");
        UserRole roleUser = new UserRole();
        roleUser.setRole(RoleName.USER);
        UserRole roleAdmin = new UserRole();
        roleAdmin.setRole(RoleName.ADMIN);
        user.setRoles(Set.of(roleUser, roleAdmin));
        Mockito.when(mockUserRepository.findByUsername("Gosho")).
                thenReturn(Optional.of(user));
        UserDetails userDetails = serviceToTest.loadUserByUsername("Gosho");
        Assertions.assertEquals(user.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        List<String> authorities = userDetails.
                getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());
        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
    }
}