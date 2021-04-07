package mainPackage.web;

import mainPackage.models.entities.ClientEntity;
import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.User;
import mainPackage.models.entities.UserRole;
import mainPackage.repositories.ClientRepository;
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

import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ClientRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clearDB() {
        clientRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void getAllModelsForBrand() throws Exception {

        this.mockMvc.perform(get("/client/find-client").param("clientName", "vailo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("Ivailo Boukliev")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void getAll() throws Exception {
        this.mockMvc.perform(get("/client/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("Ivailo Boukliev")));
    }

    private void init() {
        ClientEntity client = new ClientEntity();
        client.setClientName("Ivailo Boukliev");
        client.setClientPhoneNumber("0888666646");
        clientRepository.save(client);
        User user = new User();
        user.setUsername("Doytcho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
    }
}