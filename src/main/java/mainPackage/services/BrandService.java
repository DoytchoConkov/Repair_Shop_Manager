package mainPackage.services;

import mainPackage.models.entities.BrandEntity;
import mainPackage.models.views.BrandViewModel;

import java.util.List;

public interface BrandService {
    BrandEntity getBrand(String brand);

    List<BrandViewModel> getAll();
}
