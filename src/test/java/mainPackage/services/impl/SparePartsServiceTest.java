package mainPackage.services.impl;

import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.entities.Brand;
import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.repositories.ModelRepository;
import mainPackage.repositories.SparePartsRepository;
import mainPackage.services.ModelService;
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
    @Autowired
    private ModelService modelService;
    @MockBean
    private SparePartsRepository mockSparePartsRepository;
    @MockBean
    private ModelRepository mockModelRepository;
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
        SparePartServiceModel sparePartServiceModel=new SparePartServiceModel();
        sparePartServiceModel.setModel("P40");
        sparePartServiceModel.setBrand("Huawei");
        sparePartServiceModel.setSparePartName("LCD");
        sparePartServiceModel.setPieces(1);
        sparePartServiceModel.setPrice(BigDecimal.valueOf(100));
        Brand brand = new Brand("Huawei");
        Model model=new Model();
        model.setBrand(brand);
        model.setModelName("P40");
        Mockito.when(mockModelRepository.findByModelName("Huawei","P40")).thenReturn(model);
        sparePartsService.save(sparePartServiceModel);
    }

    //TODO add whit existing spare part save

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
        sparePartsService.deleteSparePart(1L);
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
  // TODO
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
        SparePartBindingModel sparePartBindingModel= new SparePartBindingModel();
        sparePartBindingModel.setBrand("Nokia");
        sparePartBindingModel.setModel("C3-01");
        sparePartBindingModel.setSparePartName("Battery");
        sparePartBindingModel.setPieces(2);
        sparePartBindingModel.setPrice(BigDecimal.valueOf(15));
        Brand brand = new Brand("Nokia");
        Model model = new Model("C3-01", brand);
        SparePart sparePart = new SparePart(model, "Battery");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(sparePart));
        sparePartsService.edit(1L,sparePartBindingModel);
    }

    @Test
    void update() {
        Brand brand = new Brand("Nokia");
        Model model = new Model("C3-01", brand);
        SparePart sparePart = new SparePart(model, "Back Cover");
        Mockito.when(mockSparePartsRepository.findById(1L)).thenReturn(Optional.of(sparePart));
        sparePartsService.update(1L,1);
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