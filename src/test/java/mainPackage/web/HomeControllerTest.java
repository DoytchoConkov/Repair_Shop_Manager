package mainPackage.web;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRepository;
import mainPackage.repositories.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class HomeControllerTest {
    private static final String HOME_CONTROLLER_PREFIX = "";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                HOME_CONTROLLER_PREFIX + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/index"));
    }

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void home() throws Exception {
        User user = new User();
        user.setUsername("Mitko");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.get(
                HOME_CONTROLLER_PREFIX + "/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("/home"));
        userRepository.deleteAll();
    }
}