package mainPackage.web;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRepository;
import mainPackage.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AdminControlerTest {

    private static final String ADMIN_CONTROLLER_PREFIX = "/admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    public void setup() {
        this.init();
    }
    @AfterEach
    public void clear(){
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Borko", roles = {"ADMIN"})
    void addUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ADMIN_CONTROLLER_PREFIX + "/set-user-role"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/add-or-edit-user-role"))
                .andExpect(model().attributeExists("userRolesBindingModel"));
    }

    @Test
    @WithMockUser(username = "Borko", roles = {"ADMIN"})
    void addUserRoleConfirm() throws Exception {
        User user = new User();
        user.setUsername("Valio");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_CONTROLLER_PREFIX + "/set-user-role")
                .param("username", "Valio")
                        .param("roles", "ADMIN")
                        .param("roles", "USER")
                        .param("roles", "SENIOR")
                        .with(csrf())).
                andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "Borko", roles = {"ADMIN"})
    void addUserRoleConfirmWithNoName() throws Exception {
        User user = new User();
        user.setUsername("Valio");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_CONTROLLER_PREFIX + "/set-user-role")
                .param("username", "")
                .param("roles", "USER")
                .param("roles", "SENIOR")
                .with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    private void init() {
        User user = new User();
        user.setUsername("Borko");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
    }
}