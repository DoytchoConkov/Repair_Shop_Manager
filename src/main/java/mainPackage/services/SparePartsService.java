package mainPackage.services;

import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;

import java.util.List;

public interface SparePartsService {
    List<SparePartViewModel> getAll();

    void save(SparePartServiceModel sparePart);
    List<SparePartViewModel> getAllSpareParts();

    SparePartViewModel getById(Long id);
}
