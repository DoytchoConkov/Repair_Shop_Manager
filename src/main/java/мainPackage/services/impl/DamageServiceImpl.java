package мainPackage.services.impl;

import мainPackage.models.entities.Damage;
import мainPackage.models.views.DamageViewModel;
import мainPackage.repositories.DamageRepository;
import мainPackage.services.DamageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DamageServiceImpl implements DamageService {
    private final DamageRepository damageRepository;
    private final ModelMapper modelMapper;

    public DamageServiceImpl(DamageRepository damageRepository, ModelMapper modelMapper) {
        this.damageRepository = damageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Damage getDamage(String damage) {
        Damage damageEntity = damageRepository.findByDamageName(damage).orElse(null);
        if (damageEntity == null) {
            damageEntity = new Damage(damage);
            damageEntity = damageRepository.save(damageEntity);
        }
        return damageEntity;
    }

    @Override
    public List<DamageViewModel> getAll() {
        return this.damageRepository.findAll().stream().map(d->modelMapper.map(d,DamageViewModel.class)).collect(Collectors.toList());
    }
}
