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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class FrontOfficeControllerTest {
    private static final String FRONT_OFFICE_CONTROLLER_PREFIX = "/front-office";
    private String clientId;
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

    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clearDB() {
        orderRepository.deleteAll();
        sparePartsRepository.deleteAll();
        clientRepository.deleteAll();
        damageRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        damageRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void payOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/fixed-orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/orders-ready"))
                .andExpect(model().attributeExists("orderReadyViewModels"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void payOrderRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/pay-order/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/order-details"))
                .andExpect(model().attributeExists("orderReadyViewModel"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void payOrderNow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/pay-order/{id}", orderId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void orderReceive() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/receive", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("/orders/orders-receive"))
                .andExpect(model().attributeExists("OrderReceiveBindingModel"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void orderReceiveConfirm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/receive")
                .param("serialNumber", "350103002637912")
                .param("brand", "Siemens")
                .param("model", "S35")
                .param("damage", "Disassembled")
                .param("clientName", "Pesho")
                .param("clientPhoneNumber", "0888123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void clientInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/client-info", clientId))
                .andExpect(status().isOk())
                .andExpect(view().name("/info/client-info"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"FRONT_OFFICE"})
    void orderCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                FRONT_OFFICE_CONTROLLER_PREFIX + "/order-info", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("/info/orders-info"));
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
        clientId = "" + client.getId();
        Order order = new Order();
        order.setTotalSparePartsPrice(BigDecimal.valueOf(0));
        order.setTotalRepairPrice(BigDecimal.valueOf(20));
        order.setDamage(damage);
        order.setClient(client);
        order.setModel(model);
        order.setReceiveDate(LocalDate.now());
        order.setSerialNumber("350206013591245");
        order = orderRepository.save(order);
        orderId = "" + order.getId();
    }
}