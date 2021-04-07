package mainPackage.services;

import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.entities.SparePartEntity;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface SparePartsService {
    List<SparePartViewModel> getAll();

    void save(SparePartServiceModel sparePart);

    List<SparePartViewModel> getByBrandAndModel(String brandName,String modelName);

    void deleteSparePart(Long id);

    SparePartEntity findById(Long id);

    List<SparePartViewModel> getSparePartsByBrandNameAndModel(String brandName, String modelName);

    List<SparePartViewModel> getSparePartsByBrandNameForAdd(String brandName);

    SparePartViewModel getSparePartById(Long id);

    void edit(Long id, SparePartBindingModel sparePartBindingModel);

    void update(Long id, int i);

    BigDecimal getTotalSparePartPrice(String[] sparePartsId);

    SparePartViewModel getByBrandModelName(String brandName, String modelName, String spName);

    void reduceSpareParts(List<SparePartEntity> sparePartsList);

    List<String> getSparePartsByBrandName(String brandName);
}
