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
class SparePartRestControlerTest {

    private static final String SPARE_PARTS_REST_CONTROLLER_PREFIX = "/spare-parts";
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

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getAllModelsForBrand() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/models")
                .param("brandName", "Samsung"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0]", is("Galaxy S21")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsForBrandAndModel() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts")
                .param("brandName", "Samsung")
                .param("modelName", "Galaxy S21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsForBrandForAdd() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts-for-brand")
                .param("brandName", "Samsung"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getSparePartsById() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-part-by-id")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sparePartName", is("LCD")));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getTotalSparePartPrice() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts-totalPrice")
                .param("sparePartsId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(560.0)));
    }

    @Test
    @WithMockUser(username = "Doytcho", roles = {"SENIOR"})
    void getByBrandModelAndName() throws Exception {
        this.mockMvc.perform(get(SPARE_PARTS_REST_CONTROLLER_PREFIX + "/spare-parts-name")
                .param("brandName", "Samsung")
                .param("modelName", "Galaxy S21")
                .param("spName", "LCD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sparePartName", is("LCD")));
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
    }
}