package мainPackage.repositories;

import мainPackage.models.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long> {
    Model findByModelName(String name);
    Optional<Model> findById(String id);
}
