package mainPackage.web;

import mainPackage.models.entities.*;
import mainPackage.repositories.*;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class OrderRestControllerTest {
    private static final String ORDER_REST_CONTROLLER_PREFIX = "/orders";
    private String clientId;
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

    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clearDB() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
        damageRepository.deleteAll();
        sparePartsRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Vasko", roles = {"FRONT_OFFICE"})
    void getAllModelsForBrand() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/find-order")
                .param("serialNumber", "02060"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    @Test
    @WithMockUser(username = "Vasko", roles = {"FRONT_OFFICE"})
    void getAllByClientId() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/find-clientById")
                .param("clientId", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    @Test
    @WithMockUser(username = "Vasko", roles = {"BACK_OFFICE", "SENIOR"})
    void getOrderPerPeriod() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/income")
                .param("starDate", "2021-03-01")
                .param("endDate", "2021-03-31")
                .param("technician", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].totalRepairPrice", is(20.0)));
    }

    @Test
    @WithMockUser(username = "Vasko", roles = {"BACK_OFFICE", "SENIOR"})
    void getOrdersPerDate() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/orderByDate")
                .param("date", "28-03-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("Petko")));
    }

    @Test
    @WithMockUser(username = "Vasko", roles = {"BACK_OFFICE"})
    void getAllModelsForBrandREST() throws Exception {
        this.mockMvc.perform(get(ORDER_REST_CONTROLLER_PREFIX + "/models")
                .param("brandName", "Samsung"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0]", is("Galaxy S21")));
    }

    private void init() {
        User user = new User();
        user.setUsername("Vasko");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.BACK_OFFICE).orElseThrow();
        UserRole userRole2 = userRoleRepository.findByRole(RoleName.FRONT_OFFICE).orElseThrow();
        UserRole userRole3 = userRoleRepository.findByRole(RoleName.SENIOR).orElseThrow();
        user.setRoles(Set.of(userRole, userRole2,userRole3));
        user = userRepository.save(user);
        BrandEntity brand = new BrandEntity("Samsung");
        brand = brandRepository.save(brand);
        ModelEntity model = new ModelEntity("Galaxy S21", brand);
        model = modelRepository.save(model);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        sparePart.setPieces(2);
        sparePart.setPrice(BigDecimal.valueOf(560));
        sparePartsRepository.save(sparePart);
        DamageEntity damage = new DamageEntity("Bad Audio");
        damage = damageRepository.save(damage);
        ClientEntity client = new ClientEntity();
        client.setClientName("Petko");
        client.setClientPhoneNumber("033561248");
        client = clientRepository.save(client);
        clientId = "" + client.getId();
        OrderEntity order = new OrderEntity();
        order.setClient(client);
        order.setTotalSparePartsPrice(BigDecimal.valueOf(0));
        order.setTotalRepairPrice(BigDecimal.valueOf(20));
        order.setDamage(damage);
        order.setModel(model);
        order.setUser(user);
        order.setReceiveDate(LocalDate.of(2021, 03, 28));
        order.setLeaveDate(LocalDate.of(2021, 03, 28));
        order.setUser(userRepository.getOne(user.getId()));
        order.setSerialNumber("350206013591245");
        orderRepository.save(order);
    }
}