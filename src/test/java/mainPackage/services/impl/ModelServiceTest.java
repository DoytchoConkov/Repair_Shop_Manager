package mainPackage.services.impl;

import mainPackage.models.entities.BrandEntity;
import mainPackage.models.entities.ModelEntity;
import mainPackage.models.views.ModelViewModel;
import mainPackage.repositories.ModelRepository;
import mainPackage.services.ModelService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
class ModelServiceTest {
    @Autowired
    private ModelService modelService;
    @MockBean
    private ModelRepository mockModelRepository;

    @Test
    void getModel() {
        ModelEntity model = new ModelEntity();
        BrandEntity brand = new BrandEntity("Apple");
        model.setModelName("Iphone 12 Pro Max");
        model.setBrand(brand);
        Mockito.when(mockModelRepository.findByModelName("Apple", "Iphone 12 Pro Max")).thenReturn(model);
        ModelEntity receivedModel = modelService.getModel("Apple", "Iphone 12 Pro Max");
        assertEquals(model.getModelName(), receivedModel.getModelName());
    }
    @Test
    void getModelWhichNotExistName() {
        ModelEntity model = new ModelEntity();
        BrandEntity brand = new BrandEntity("Apple");
        model.setModelName("Iphone 12 Pro Max");
        model.setBrand(brand);
        Mockito.when(mockModelRepository.findByModelName("Samsung", "Galaxy S21")).thenReturn(null);
        modelService.getModel("Apple", "Iphone 12 Pro Max");
        verify(mockModelRepository,times(1)).save(any());
    }
    @Test
    void getModelWhichNotExistBrand() {
        ModelEntity model = new ModelEntity();
        BrandEntity brand = new BrandEntity("Apple");
        model.setModelName("Iphone 12 Pro Max");
        model.setBrand(brand);
        Mockito.when(mockModelRepository.findByModelName("Samsung", "Galaxy S21")).thenReturn(model);
        modelService.getModel("Apple", "Iphone 12 Pro Max");
        verify(mockModelRepository,times(1)).save(any());
    }

    @Test
    void getAll() {
        List<ModelEntity> models = new ArrayList<>();
        models.add(new ModelEntity());
        Mockito.when(mockModelRepository.findAll()).thenReturn(models);
        List<ModelViewModel> allModels = modelService.getAll();
        assertEquals(models.size(), allModels.size());
    }
}