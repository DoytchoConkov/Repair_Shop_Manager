package mainPackage.services;

import mainPackage.models.entities.Brand;
import mainPackage.models.views.BrandViewModel;

import java.util.List;

public interface BrandService {
    Brand getBrand(String brand);

    List<BrandViewModel> getAll();
}
