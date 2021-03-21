package mainPackage.services;

import mainPackage.models.entities.Brand;
import mainPackage.models.views.BrandViewModel;
import mainPackage.repositories.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
class BrandServiceTest {

    @Autowired
    private BrandService brandService;
    @MockBean
    private BrandRepository mockBrandRepository;


    @Test
    void getBrand() {


    }

    @Test
    void getAll() {
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand());
        Mockito.when(mockBrandRepository.findAll()).thenReturn(brands);
        List<BrandViewModel> allBrands = brandService.getAll();
        assertEquals(brands.size(),allBrands.size());
    }
}