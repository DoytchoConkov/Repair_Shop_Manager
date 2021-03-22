package mainPackage.services;

import mainPackage.models.entities.Brand;
import mainPackage.models.entities.Model;
import mainPackage.models.views.ModelViewModel;
import mainPackage.repositories.ModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ModelServiceTest {
    @Autowired
    private ModelService modelService;
    @MockBean
    private ModelRepository mockModelRepository;

    @Test
    void getModel() {
        Model model = new Model();
        Brand brand = new Brand("Apple");
        model.setModelName("Iphone 12 Pro Max");
        model.setBrand(brand);
        Mockito.when(mockModelRepository.findByModelName("Apple", "Iphone 12 Pro Max")).thenReturn(model);
        Model receivedModel = modelService.getModel("Apple", "Iphone 12 Pro Max");
        assertEquals(model.getModelName(), receivedModel.getModelName());
    }

    void getModelWhichNotExistName() {
        Model model = new Model();
        Brand brand = new Brand("Apple");
        model.setModelName("Iphone 12 Pro Max");
        model.setBrand(brand);
        Mockito.when(mockModelRepository.findByModelName("Samsung", "Galaxy S21")).thenReturn(model);
        Model receivedModel = modelService.getModel("Apple", "Iphone 12 Pro Max");
        assertEquals(model.getModelName(), receivedModel.getModelName());
    }
    void getModelWhichNotExistBrand() {
        Model model = new Model();
        Brand brand = new Brand("Apple");
        model.setModelName("Iphone 12 Pro Max");
        model.setBrand(brand);
        Mockito.when(mockModelRepository.findByModelName("Samsung", "Galaxy S21")).thenReturn(model);
        Model receivedModel = modelService.getModel("Apple", "Iphone 12 Pro Max");
        assertEquals(model.getModelName(), receivedModel.getModelName());
    }

    @Test
    void getAll() {
        List<Model> models = new ArrayList<>();
        models.add(new Model());
        Mockito.when(mockModelRepository.findAll()).thenReturn(models);
        List<ModelViewModel> allModels = modelService.getAll();
        assertEquals(models.size(), allModels.size());
    }

    @Test
    void getByBrandNameWithValidBrandName() {
        List<Model> models = new ArrayList<>();
        Model model = new Model();
        Brand brand = new Brand("Apple");
        model.setBrand(brand);
        model.setModelName("Iphone 6s");
        models.add(model);
        Mockito.when(mockModelRepository.findByBrandName("Apple")).thenReturn(List.of("Apple"));
        List<String> allModels = modelService.getByBrandName("Apple");
        assertEquals(models.size(), allModels.size());
    }

    @Test
    void getByBrandNameWithNotValidBrandName() {
        Mockito.when(mockModelRepository.findByBrandName("Apple")).thenReturn(new ArrayList<>());
        List<String> allModels = modelService.getByBrandName("Apple");
        assertTrue(allModels.isEmpty());
    }
}