package mainPackage.repositories;

import mainPackage.models.entities.Damage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DamageRepository extends JpaRepository<Damage,Long> {

    Damage findByDamageName(String damage);
}
