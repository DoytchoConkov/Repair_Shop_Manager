package mainPackage.web;

import mainPackage.models.entities.*;
import mainPackage.repositories.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class OrderRestControllerTest {
    private static final String ORDER_REST_CONTROLLER_PREFIX = "/orders";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SparePartsRepository sparePartsRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private DamageRepository damageRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void setup() {
        this.init();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getAllModelsForBrand() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/find-order")
                .param("serialNumber", "020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getAllByClientId() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/find-clientById")
                .param("clientId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
//                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getOrderPerPeriod() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/income")
                .param("starDate", "2021-03-01")
                .param("endDate", "2021-03-31")
                .param("technician", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
//                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getOrdersPerDate() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/orderByDate")
                .param("date", "28-03-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
//                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    private void init() {
        User user = new User();
        user.setUsername("Gosho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        Brand brand = new Brand("Samsung");
        brand = brandRepository.save(brand);
        Model model = new Model("Galaxy S21", brand);
        model = modelRepository.save(model);
        SparePart sparePart = new SparePart(model, "LCD");
        sparePart.setPieces(2);
        sparePart.setPrice(BigDecimal.valueOf(560));
        sparePartsRepository.save(sparePart);
        Damage damage = new Damage("Bad Audio");
        damage = damageRepository.save(damage);
        Client client = new Client();
        client.setClientName("Petko");
        client.setClientPhoneNumber("033561248");
        client = clientRepository.save(client);
        Order order = new Order();
        order.setTotalSparePartsPrice(BigDecimal.valueOf(0));
        order.setTotalRepairPrice(BigDecimal.valueOf(20));
        order.setDamage(damage);
        order.setClient(client);
        order.setModel(model);
        order.setReceiveDate(LocalDate.now());
        order.setUser(userRepository.getOne(1L));
        order.setSerialNumber("350206013591245");
        orderRepository.save(order);
    }
}