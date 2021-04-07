package mainPackage.web;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRepository;
import mainPackage.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserControlerTest {
    private static final String USER_CONTROLLER_PREFIX = "/users";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/auth-register"));
    }

    @Test
    void registerConfirm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PREFIX + "/register")
                .param("username", "Gosho")
                .param("password", "12345")
                .param("confirmPassword", "12345")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void registerConfirmWithDifferentConfirmPassword() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PREFIX + "/register")
                .param("username", "Gosho")
                .param("password", "12345")
                .param("confirmPassword", "11111")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void registerConfirmWithWrongBindingModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PREFIX + "/register")
                .param("username", "Gos")
                .param("password", "1")
                .param("confirmPassword", "1")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void registerConfirmWithExistUserName() throws Exception {
        User user = new User();
        user.setUsername("Gosho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PREFIX + "/register")
                .param("username", "Gosho")
                .param("password", "11111")
                .param("confirmPassword", "11111")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/auth-login"));
    }

    @Test
    void failedLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(USER_CONTROLLER_PREFIX + "/login-error")
                .param("name", "Valio")
                .param("password", "11111")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }
}