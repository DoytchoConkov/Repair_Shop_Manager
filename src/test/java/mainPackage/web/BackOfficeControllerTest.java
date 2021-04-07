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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class BackOfficeControllerTest {
    private static final String BACKOFFICE_CONTROLLER_PREFIX = "/back-office";
    private String orderId;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private DamageRepository damageRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SparePartsRepository sparePartsRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clear() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
        sparePartsRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    public void clearDB() {
        orderRepository.deleteAll();
        sparePartsRepository.deleteAll();
        clientRepository.deleteAll();
        damageRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"BACK_OFFICE"})
    void ordersToRepairAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                BACKOFFICE_CONTROLLER_PREFIX + "/not-fixed"))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/orders-not-ready"))
                .andExpect(model().attributeExists("orderNotReadyViewModels"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"BACK_OFFICE"})
    void fixOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                BACKOFFICE_CONTROLLER_PREFIX + "/fix/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/order-fixing"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"BACK_OFFICE", "SENIOR"})
    void fixOrderConfirm() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.post(
                BACKOFFICE_CONTROLLER_PREFIX + "/fix/{id}", orderId)
                .param("totalPrice", "100")
                .param("sparePartPrice", "0")
                .param("sparePartIds", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/back-office/not-fixed"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"BACK_OFFICE"})
    void addSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                BACKOFFICE_CONTROLLER_PREFIX + "/add-spare-part"))
                .andExpect(status().isOk())
                .andExpect(view().name("/spare-parts/add-spare-part"))
                .andExpect(model().attributeExists("spareParts"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"BACK_OFFICE"})
    void addSparePartConfirm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                BACKOFFICE_CONTROLLER_PREFIX + "/add-spare-part")
                .param("brand", "Xiaomi")
                .param("model", "Mi 9")
                .param("sparePartName", "Speaker")
                .param("pieces", "1")
                .param("price", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    private void init() {
        User user = new User();
        user.setUsername("Doytcho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        UserRole userRole2 = userRoleRepository.findByRole(RoleName.BACK_OFFICE).orElseThrow();
        user.setRoles(Set.of(userRole, userRole2));
        user = userRepository.save(user);
        Long userId = user.getId();
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
        OrderEntity order = new OrderEntity();
        order.setTotalSparePartsPrice(BigDecimal.valueOf(0));
        order.setTotalRepairPrice(BigDecimal.valueOf(20));
        order.setDamage(damage);
        order.setClient(client);
        order.setModel(model);
        order.setReceiveDate(LocalDate.now());
        order.setSerialNumber("350206013591245");
        order.setUser(userRepository.findById(userId).orElseThrow());
        order = orderRepository.save(order);
        orderId = "" + order.getId();
    }
}