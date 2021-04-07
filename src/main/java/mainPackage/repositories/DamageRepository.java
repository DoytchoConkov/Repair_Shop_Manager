package mainPackage.repositories;

import mainPackage.models.entities.DamageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageRepository extends JpaRepository<DamageEntity,Long> {

    DamageEntity findByDamageName(String damage);
}
