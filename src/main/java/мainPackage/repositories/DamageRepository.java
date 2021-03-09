package мainPackage.repositories;

import мainPackage.models.entities.Damage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DamageRepository extends JpaRepository<Damage,Long> {

    Optional<Damage> findByDamageName(String damage);
}
