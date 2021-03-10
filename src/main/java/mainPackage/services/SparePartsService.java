package mainPackage.services;

import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;

import java.util.List;

public interface SparePartsService {
    List<SparePartViewModel> getAll();

    void save(SparePartServiceModel sparePart);

    SparePartViewModel getById(Long id);

    List<SparePartViewModel> getByBrandAndModel(String brandName,String modelName);

    void deleteSparePart(Long id);
}
