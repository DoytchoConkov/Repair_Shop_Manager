package mainPackage.services;

import mainPackage.models.entities.Model;
import mainPackage.models.views.ModelViewModel;

import java.util.List;

public interface ModelService {
    Model getModel(String brand, String model);

    List<ModelViewModel> getAll();

    List<String> getByBrandName(String brandName);
}
