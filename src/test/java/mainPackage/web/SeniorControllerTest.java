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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class SeniorControllerTest {
    private static final String SENIOR_CONTROLLER_PREFIX = "/senior";
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

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void viewSpareParts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/edit-spare-parts"))
                .andExpect(status().isOk())
                .andExpect(view().name("/extended/edit-spare-parts"))
                .andExpect(model().attributeExists("brands"));
    }

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void editOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                SENIOR_CONTROLLER_PREFIX + "/edit-orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("/extended/edit-orders"))
                .andExpect(model().attributeExists("orderReadyViewModels"));
    }

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
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
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void editSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(
                SENIOR_CONTROLLER_PREFIX + "/spare_part/edit/{id}", 1)
                .param("brand", "Samsung")
                .param("model", "Galaxy S21")
                .param("sparePartName", "LCD")
                .param("price", "450")
                .param("pieces", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void deleteSparePart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                SENIOR_CONTROLLER_PREFIX + "/spare_part/delete/{id}", 1)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void deleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(
                SENIOR_CONTROLLER_PREFIX + "/order/delete/{id}", 1)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Mitko", roles = {"ADMIN"})
    void makeOrderNotFixed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                SENIOR_CONTROLLER_PREFIX + "/order/make-not-fixed/{id}", 1)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    private void init() {
        User user = new User();
        user.setUsername("Mitko");
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