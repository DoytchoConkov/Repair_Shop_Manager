package mainPackage.services;

import mainPackage.models.entities.Damage;
import mainPackage.models.views.DamageViewModel;

import java.util.List;

public interface DamageService {
    Damage getDamage(String damage);

    List<DamageViewModel> getAll();
}
