package mainPackage.web;

import mainPackage.models.entities.*;
import mainPackage.repositories.*;
import org.junit.Before;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class SeniorControllerTest {
    private static final String SENIOR_CONTROLLER_PREFIX = "/senior";
    private String id;
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
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
    void viewSpareParts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/edit-spare-parts"))
                .andExpect(status().isOk())
                .andExpect(view().name("/extended/edit-spare-parts"))
                .andExpect(model().attributeExists("brands"));
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
    void editOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/edit-orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("/extended/edit-orders"))
                .andExpect(model().attributeExists("orderReadyViewModels"));
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
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
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
    void editSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(
                SENIOR_CONTROLLER_PREFIX + "/spare_part/edit/{id}", id)
                .param("brand", "Samsung")
                .param("model", "Galaxy S21")
                .param("sparePartName", "LCD")
                .param("price", "450")
                .param("pieces", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
    void deleteSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                SENIOR_CONTROLLER_PREFIX + "/spare_part/delete/{id}", id)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
    void deleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                SENIOR_CONTROLLER_PREFIX + "/order/delete/{id}", id)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Gosho", roles = {"ADMIN"})
    void makeOrderNotFixed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                SENIOR_CONTROLLER_PREFIX + "/order/make-not-fixed/{id}", id)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    private void init() {
        User user = new User();
        user.setUsername("Gosho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.ADMIN).orElseThrow();
        user.setRoles(Set.of(userRole));
        user = userRepository.save(user);
        Long userId=user.getId();
        Brand brand = new Brand("Samsung");
        brand = brandRepository.save(brand);
        Model model = new Model("Galaxy S21", brand);
        model = modelRepository.save(model);
        SparePart sparePart = new SparePart(model, "LCD");
        sparePart.setPieces(2);
        sparePart.setPrice(BigDecimal.valueOf(560));
        sparePart = sparePartsRepository.save(sparePart);
        id = "" + sparePart.getId();
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
        order.setUser(userRepository.getOne(userId));
        order.setSerialNumber("350206013591245");
        orderRepository.save(order);
    }
}