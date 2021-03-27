package mainPackage.web;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.UserRepository;
import mainPackage.repositories.UserRoleRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class BackOfficeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void setup() {
        this.init();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"ADMIN"})
    void ordersToRepairAll() {
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"ADMIN"})
    void fixOrder() {
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"ADMIN"})
    void fixOrderConfirm() {
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"ADMIN"})
    void addSparePart() {
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"ADMIN"})
    void addSparePartConfirm() {
    }

    private void init() {
        User user = new User();
        user.setUsername("Doytcho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
    }
}