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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class BackOfficeControllerTest {
    private static final String BACKOFFICE_CONTROLLER_PREFIX = "/back-office";
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

    @Before
    public void setup() {
        this.init();
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
                BACKOFFICE_CONTROLLER_PREFIX + "/fix/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/order-fixing"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"BACK_OFFICE","SENIOR"})
    void fixOrderConfirm() throws Exception {
        this.init();
        User user = new User();
        user.setUsername("Doytcho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        UserRole userRole2 = userRoleRepository.findByRole(RoleName.BACK_OFFICE).orElseThrow();
        user.setRoles(Set.of(userRole,userRole2));
        mockMvc.perform(MockMvcRequestBuilders.post(
                BACKOFFICE_CONTROLLER_PREFIX + "/fix/{id}", 1)
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
                .andExpect(view().name("/extended/add-spare-part"))
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
        order.setSerialNumber("350206013591245");
        orderRepository.save(order);
    }
}