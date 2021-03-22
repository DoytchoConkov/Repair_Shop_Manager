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
import java.util.Optional;

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
        Brand brand = new Brand("Apple");
        Mockito.when(mockBrandRepository.findByBrandName("Apple")).thenReturn(java.util.Optional.of(brand));
        Brand receivedBrand=brandService.getBrand("Apple");
        assertEquals(brand.getBrandName(),receivedBrand.getBrandName());
    }

    @Test
    void getByMissingBrand() {
        Brand brand = new Brand("Nokia");
        Mockito.when(mockBrandRepository.findByBrandName("Nokia")).thenReturn(Optional.empty());
        Mockito.when(mockBrandRepository.save(brand)).thenReturn(brand);
        Brand receivedBrand=brandService.getBrand("Nokia");
        assertEquals(brand.getBrandName(),receivedBrand.getBrandName());
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