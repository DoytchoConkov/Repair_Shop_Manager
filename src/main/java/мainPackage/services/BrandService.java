package мainPackage.services;

import мainPackage.models.entities.Brand;
import мainPackage.models.views.BrandViewModel;

import java.util.List;

public interface BrandService {
    Brand getBrand(String brand);

    List<BrandViewModel> getAll();
}
