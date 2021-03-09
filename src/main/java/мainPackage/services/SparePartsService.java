package мainPackage.services;

import мainPackage.models.services.SparePartServiceModel;
import мainPackage.models.views.SparePartViewModel;

import java.util.List;

public interface SparePartsService {
    List<SparePartViewModel> getAll();

    void save(SparePartServiceModel sparePart);
    List<SparePartViewModel> getAllSpareParts();

    SparePartViewModel getById(Long id);
}
