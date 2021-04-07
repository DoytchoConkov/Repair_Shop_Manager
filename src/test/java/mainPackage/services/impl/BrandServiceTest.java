package mainPackage.services.impl;

import mainPackage.models.entities.BrandEntity;
import mainPackage.models.views.BrandViewModel;
import mainPackage.repositories.BrandRepository;
import mainPackage.services.BrandService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
class BrandServiceTest {

    @Autowired
    private BrandService brandService;
    @MockBean
    private BrandRepository mockBrandRepository;


    @Test
    void getBrand() {
        BrandEntity brand = new BrandEntity("Apple");
        Mockito.when(mockBrandRepository.findByBrandName("Apple")).thenReturn(brand);
        BrandEntity receivedBrand=brandService.getBrand("Apple");
        assertEquals(brand.getBrandName(),receivedBrand.getBrandName());
    }

    @Test
    void getByMissingBrand() {
        BrandEntity brand = new BrandEntity("Nokia");
        Mockito.when(mockBrandRepository.findByBrandName("Nokia")).thenReturn(null);
        BrandEntity receivedBrand=brandService.getBrand("Nokia");
        verify(mockBrandRepository,times(1)).save(any());
    }

    @Test
    void getAll() {
        List<BrandEntity> brands = new ArrayList<>();
        brands.add(new BrandEntity());
        Mockito.when(mockBrandRepository.findAll()).thenReturn(brands);
        List<BrandViewModel> allBrands = brandService.getAll();
        assertEquals(brands.size(),allBrands.size());
    }
}