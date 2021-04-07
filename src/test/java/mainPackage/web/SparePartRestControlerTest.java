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
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class SparePartRestControlerTest {

    private static final String SPARE_PARTS_REST_CONTROLLER_PREFIX = "/spare-parts";
    private String id;
    @Autowired
    private MockMvc mockMvc;
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

    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clearRepository() {
        sparePartsRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsForBrandAndModel() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts")
                .param("brandName", "Pocophone")
                .param("modelName", "F1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsForBrandForAdd() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts-for-brand")
                .param("brandName", "Pocophone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsById() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-part-by-id")
                .param("id", this.id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getTotalSparePartPrice() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts-totalPrice")
                .param("sparePartsId", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(200.0)));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getByBrandModelAndName() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts-name")
                .param("brandName", "Pocophone")
                .param("modelName", "F1")
                .param("spName", "LCD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsForBrand() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/models")
                .param("brandName", "Pocophone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]", is("F1")));
    }

    private void init() {
        User user = new User();
        user.setUsername("Doytcho");
        user.setPassword("$2a$10$Dr9P8sptTPVfPyE0ynbXJOd9BYAwMCPL3l.NIe29F4LnNyZhi0lSu");
        UserRole userRole = userRoleRepository.findByRole(RoleName.BACK_OFFICE).orElseThrow();
        UserRole userRole2 = userRoleRepository.findByRole(RoleName.FRONT_OFFICE).orElseThrow();
        UserRole userRole3 = userRoleRepository.findByRole(RoleName.SENIOR).orElseThrow();
        user.setRoles(Set.of(userRole, userRole2, userRole3));
        user = userRepository.save(user);
        BrandEntity brand = new BrandEntity("Pocophone");
        brand = brandRepository.save(brand);
        ModelEntity model = new ModelEntity("F1", brand);
        model = modelRepository.save(model);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        sparePart.setPieces(1);
        sparePart.setPrice(BigDecimal.valueOf(200));
        sparePart = sparePartsRepository.save(sparePart);
        this.id = "" + sparePart.getId();
    }
}