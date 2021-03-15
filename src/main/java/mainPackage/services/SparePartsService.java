package mainPackage.services;

import mainPackage.models.entities.SparePart;
import mainPackage.models.services.SparePartServiceModel;
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

    List<SparePartViewModel> getSparePartsByBrandNameAndModel(String brandName, String modelName);

    List<SparePartViewModel> getSparePartsByBrandNameForAdd(String brandName);

    SparePartViewModel getSparePartById(Long id);
}
