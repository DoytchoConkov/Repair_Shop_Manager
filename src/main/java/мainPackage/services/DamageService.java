package мainPackage.services;

import мainPackage.models.entities.Damage;
import мainPackage.models.views.DamageViewModel;

import java.util.List;

public interface DamageService {
    Damage getDamage(String damage);

    List<DamageViewModel> getAll();
}
