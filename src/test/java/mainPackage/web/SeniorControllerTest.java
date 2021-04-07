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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class SeniorControllerTest {
    private static final String SENIOR_CONTROLLER_PREFIX = "/senior";
    private String sparePartId;
    private String orderId;
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
    public void deleteSparePartRepository() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
        sparePartsRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        userRepository.deleteAll();
        damageRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void viewSpareParts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/edit-spare-parts"))
                .andExpect(status().isOk())
                .andExpect(view().name("/spare-parts/edit-spare-parts"))
                .andExpect(model().attributeExists("brands"));
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void editOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/edit-orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/edit-orders"))
                .andExpect(model().attributeExists("orderReadyViewModels"));
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void incomeInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/income-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("/info/income-info"))
                .andExpect(model().attributeExists("startDate"))
                .andExpect(model().attributeExists("endDate"))
                .andExpect(model().attributeExists("technicians"));
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void editSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(
                SENIOR_CONTROLLER_PREFIX + "/spare_part/edit/{id}", sparePartId)
                .param("brand", "Samsung")
                .param("model", "Galaxy S21")
                .param("sparePartName", "LCD")
                .param("price", "450")
                .param("pieces", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void deleteSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                SENIOR_CONTROLLER_PREFIX + "/spare_part/delete/{id}", sparePartId)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void deleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                SENIOR_CONTROLLER_PREFIX + "/order/delete/{id}", orderId)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"SENIOR"})
    void makeOrderNotFixed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                SENIOR_CONTROLLER_PREFIX + "/order/make-not-fixed/{id}", orderId)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    private void init() {
        User user = new User();
        user.setUsername("Gosho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        UserRole userRole2 = userRoleRepository.findByRole(RoleName.SENIOR).orElseThrow();
        user.setRoles(Set.of(userRole,userRole2));
        user = userRepository.save(user);
        Long userId = user.getId();
        BrandEntity brand = new BrandEntity("Samsung");
        brand = brandRepository.save(brand);
        ModelEntity model = new ModelEntity("Galaxy S21", brand);
        model = modelRepository.save(model);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        sparePart.setPieces(2);
        sparePart.setPrice(BigDecimal.valueOf(560));
        sparePart = sparePartsRepository.save(sparePart);
        sparePartId = "" + sparePart.getId();
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
        order.setUser(userRepository.getOne(userId));
        order.setSerialNumber("350206013591245");
        order = orderRepository.save(order);
        orderId = "" + order.getId();
    }
}