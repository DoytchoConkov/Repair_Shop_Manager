package mainPackage.services;

import mainPackage.models.entities.SparePart;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartAddViewModel;
import mainPackage.models.views.SparePartViewModel;

import java.util.List;

public interface SparePartsService {
    List<SparePartViewModel> getAll();

    void save(SparePartServiceModel sparePart);

    SparePartViewModel getById(Long id);

    List<SparePartViewModel> getByBrandAndModel(String brandName,String modelName);

    void deleteSparePart(Long id);

    SparePart findById(Long id);

    void update(SparePart sparePart);

    List<String> getModelsByBrandName(String brandName);

    List<String> getSparePartsByBrandNameAndModel(String brandName, String modelName);

    List<SparePartAddViewModel> getSparePartsByBrandNameAndModelForAdd(String brandName, String modelName);

    List<SparePartAddViewModel> getSparePartsByBrandNameForAdd(String brandName);
}
