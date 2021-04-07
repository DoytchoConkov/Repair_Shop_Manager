package mainPackage.services.impl;

import mainPackage.errors.SparePartIdNotFoundException;
import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.entities.BrandEntity;
import mainPackage.models.entities.ModelEntity;
import mainPackage.models.entities.SparePartEntity;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.repositories.ModelRepository;
import mainPackage.repositories.SparePartsRepository;
import mainPackage.services.SparePartsService;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
class SparePartsServiceTest {
    @Autowired
    private SparePartsService sparePartsService;
    @MockBean
    private SparePartsRepository mockSparePartsRepository;
    @MockBean
    private ModelRepository mockModelRepository;

    @Test
    void getAll() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        List<SparePartEntity> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple", "Iphone Xs", "LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.findAll()).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getAll();
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void save() {
        SparePartServiceModel sparePartServiceModel = new SparePartServiceModel();
        sparePartServiceModel.setModel("P40");
        sparePartServiceModel.setBrand("Huawei");
        sparePartServiceModel.setSparePartName("LCD");
        sparePartServiceModel.setPieces(1);
        sparePartServiceModel.setPrice(BigDecimal.valueOf(100));
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity();
        model.setBrand(brand);
        model.setModelName("P40");
        Mockito.when(mockModelRepository.findByModelName("Huawei", "P40")).thenReturn(model);
        sparePartsService.save(sparePartServiceModel);
        verify(mockSparePartsRepository,times(1)).save(any());
    }

    @Test
    void getByBrandAndModel() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        List<SparePartEntity> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple", "Iphone Xs", "LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.findByBrandAndModel("Apple", "Iphone Xs")).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getByBrandAndModel("Apple", "Iphone Xs");
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void deleteSparePart() {
        sparePartsService.deleteSparePart(1L);
        verify(mockSparePartsRepository, times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity expected = new SparePartEntity(model, "LCD");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(expected));
        SparePartEntity actual = sparePartsService.findById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdWithWrongId() {
        Assertions.assertThrows(SparePartIdNotFoundException.class, () -> sparePartsService.findById(15L));
    }

    @Test
    void getSparePartsByBrandNameAndModel() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        List<SparePartEntity> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple", "Iphone Xs", "LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.getSparePartsByBrandNameAndModel("Apple", "Iphone Xs")).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getSparePartsByBrandNameAndModel("Apple", "Iphone Xs");
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void getSparePartsByBrandNameForAdd() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity sparePart = new SparePartEntity(model, "LCD");
        List<SparePartEntity> spareParts = List.of(sparePart);
        SparePartViewModel sparePartViewModel = new SparePartViewModel("Apple", "Iphone Xs", "LCD");
        List<SparePartViewModel> expected = new ArrayList<>();
        expected.add(sparePartViewModel);
        Mockito.when(mockSparePartsRepository.getSparePartsBrandName("Apple")).thenReturn(spareParts);
        List<SparePartViewModel> actual = sparePartsService.getSparePartsByBrandNameForAdd("Apple");
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void getSparePartsByBrandNameForAddWithNotExistingBrand() {
        Mockito.when(mockSparePartsRepository.getSparePartsBrandName("Siemens")).thenReturn(new ArrayList<>());
        List<SparePartViewModel> actual = sparePartsService.getSparePartsByBrandNameForAdd("Siemens");
        assertEquals(0, actual.size());
    }

    @Test
    void getSparePartById() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity expected = new SparePartEntity(model, "LCD");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(expected));
        SparePartViewModel actual = sparePartsService.getSparePartById(1L);
        assertEquals(expected.getModel().getBrand().getBrandName(), actual.getBrand());
        assertEquals(expected.getModel().getModelName(), actual.getModel());
        assertEquals(expected.getSparePartName(), actual.getSparePartName());
    }

    @Test
    void edit() {
        SparePartBindingModel sparePartBindingModel = new SparePartBindingModel();
        sparePartBindingModel.setBrand("Nokia");
        sparePartBindingModel.setModel("C3-01");
        sparePartBindingModel.setSparePartName("Battery");
        sparePartBindingModel.setPieces(2);
        sparePartBindingModel.setPrice(BigDecimal.valueOf(15));
        BrandEntity brand = new BrandEntity("Nokia");
        ModelEntity model = new ModelEntity("C3-01", brand);
        SparePartEntity sparePart = new SparePartEntity(model, "Battery");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(sparePart));
        sparePartsService.edit(1L,sparePartBindingModel);
        verify(mockSparePartsRepository,times(1)).save(any());
    }

    @Test
    void update() {
        BrandEntity brand = new BrandEntity("Nokia");
        ModelEntity model = new ModelEntity("C3-01", brand);
        SparePartEntity sparePart = new SparePartEntity(model, "Back Cover");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(sparePart));
        sparePartsService.update(1L, 1);
        verify(mockSparePartsRepository,times(1)).save(any());
    }

    @Test
    void getTotalSparePartPrice() {
        Long[] ids = {1L, 3L};
        String[] idString = {"1", "3"};
        Mockito.when(mockSparePartsRepository.getTotalPrice(ids)).thenReturn(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), sparePartsService.getTotalSparePartPrice(idString));
    }

    @Test
    void getByBrandModelName() {
        BrandEntity brand = new BrandEntity("Apple");
        ModelEntity model = new ModelEntity("Iphone Xs", brand);
        SparePartEntity expected = new SparePartEntity(model, "LCD");
        Mockito.when(mockSparePartsRepository.getByBrandModelName("Apple", "Iphone Xs", "LCD")).thenReturn(expected);
        SparePartViewModel actual = sparePartsService.getByBrandModelName("Apple", "Iphone Xs", "LCD");
        assertEquals(expected.getModel().getBrand().getBrandName(), actual.getBrand());
        assertEquals(expected.getModel().getModelName(), actual.getModel());
        assertEquals(expected.getSparePartName(), actual.getSparePartName());
    }
}