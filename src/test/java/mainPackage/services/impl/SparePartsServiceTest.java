package mainPackage.services.impl;

import mainPackage.models.entities.Brand;
import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.repositories.SparePartsRepository;
import mainPackage.services.SparePartsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
class SparePartsServiceTest {
    @Autowired
    private SparePartsService sparePartsService;
    @MockBean
    private SparePartsRepository mockSparePartsRepository;

    @Test
    void getAll() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart sparePart = new SparePart(model, "LCD");
        List<SparePart> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple","Iphone Xs","LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.findAll()).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getAll();
        assertEquals(expected.size(),actual.size());
    }

    @Test
    void save() {
//        SparePartServiceModel sparePartServiceModel = new SparePartServiceModel("Samsung","Galaxy S20",
//                "Glass refurbish", BigDecimal.valueOf(250),2);
//        Long expected= sPRepository.count()+1;
//        sparePartsService.save(sparePartServiceModel);
//        Long newSize =sPRepository.count();
//        assertEquals(expected,newSize);
    }

    @Test
    void getByBrandAndModel() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart sparePart = new SparePart(model, "LCD");
        List<SparePart> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple","Iphone Xs","LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.findByBrandAndModel("Apple","Iphone Xs")).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getByBrandAndModel("Apple","Iphone Xs");
        assertEquals(expected.size(),actual.size());
    }

    @Test
    void deleteSparePart() {
        //TODO
    }

    @Test
    void findById() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart expected = new SparePart(model, "LCD");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(expected));
        SparePart actual = sparePartsService.findById(1L);
        assertEquals(expected,actual);
    }
    @Test
    void findByIdWithWrongId() {
//        Mockito.when(mockSparePartsRepository.findById(10L)).thenReturn(Optional.empty());
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            SparePart actual = sparePartsService.findById(1L);
//        });
//
//        String expectedMessage = "Invalid Id";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void getSparePartsByBrandNameAndModel() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart sparePart = new SparePart(model, "LCD");
        List<SparePart> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple","Iphone Xs","LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.getSparePartsByBrandNameAndModel("Apple","Iphone Xs")).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getSparePartsByBrandNameAndModel("Apple","Iphone Xs");
        assertEquals(expected.size(),actual.size());
    }

    @Test
    void getSparePartsByBrandNameForAdd() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart sparePart = new SparePart(model, "LCD");
        List<SparePart> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple","Iphone Xs","LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.getSparePartsBrandName("Apple")).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getSparePartsByBrandNameForAdd("Apple");
        assertEquals(expected.size(),actual.size());
    }
    @Test
    void getSparePartsByBrandNameForAddWithNotExistingBrand() {
        Mockito.when(mockSparePartsRepository.getSparePartsBrandName("Siemens")).thenReturn(new ArrayList<>());
        List<SparePartViewModel> actual = sparePartsService.getSparePartsByBrandNameForAdd("Siemens");
        assertEquals(0,actual.size());
    }

    @Test
    void getSparePartById() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart expected = new SparePart(model, "LCD");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(expected));
        SparePartViewModel actual = sparePartsService.getSparePartById(1L);

        assertEquals(expected.getModel().getBrand().getBrandName(),actual.getBrand());
        assertEquals(expected.getModel().getModelName(),actual.getModel());
        assertEquals(expected.getSparePartName(),actual.getSparePartName());
    }

    @Test
    void edit() {

    }

    @Test
    void update() {

    }

    @Test
    void getTotalSparePartPrice() {
        Long[] ids={1L,3L};
        String[] idString={"1","3"};
        Mockito.when(mockSparePartsRepository.getTotalPrice(ids)).thenReturn(BigDecimal.valueOf(100));
assertEquals(BigDecimal.valueOf(100),sparePartsService.getTotalSparePartPrice(idString));
    }

    @Test
    void getByBrandModelName() {
        Brand brand = new Brand("Apple");
        Model model = new Model("Iphone Xs", brand);
        SparePart expected = new SparePart(model, "LCD");
        Mockito.when(mockSparePartsRepository.getByBrandModelName("Apple","Iphone Xs","LCD")).thenReturn(expected);
        SparePartViewModel actual = sparePartsService.getByBrandModelName("Apple","Iphone Xs","LCD");

        assertEquals(expected.getModel().getBrand().getBrandName(),actual.getBrand());
        assertEquals(expected.getModel().getModelName(),actual.getModel());
        assertEquals(expected.getSparePartName(),actual.getSparePartName());
    }
}