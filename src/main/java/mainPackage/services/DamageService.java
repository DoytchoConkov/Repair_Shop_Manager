package mainPackage.services;

import mainPackage.models.entities.DamageEntity;
import mainPackage.models.views.DamageViewModel;

import java.util.List;

public interface DamageService {
    DamageEntity getDamage(String damage);

    List<DamageViewModel> getAll();
}
