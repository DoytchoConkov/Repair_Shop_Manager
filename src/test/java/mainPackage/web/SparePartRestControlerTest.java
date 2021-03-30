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

    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clearRepository() {
        sparePartsRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getAllModelsForBrand() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/models")
                .param("brandName", "Pocophone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0]", is("F1")));
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

    private void init() {
        Brand brand = new Brand("Pocophone");
        brand = brandRepository.save(brand);
        Model model = new Model("F1", brand);
        model = modelRepository.save(model);
        SparePart sparePart = new SparePart(model, "LCD");
        sparePart.setPieces(1);
        sparePart.setPrice(BigDecimal.valueOf(200));
        sparePart = sparePartsRepository.save(sparePart);
        this.id = "" + sparePart.getId();
    }
}