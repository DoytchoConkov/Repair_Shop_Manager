package mainPackage.services;

import mainPackage.models.entities.ModelEntity;
import mainPackage.models.views.ModelViewModel;

import java.util.List;

public interface ModelService {
    ModelEntity getModel(String brand, String model);

    List<ModelViewModel> getAll();

    List<String> getByBrandName(String brandName);
}
