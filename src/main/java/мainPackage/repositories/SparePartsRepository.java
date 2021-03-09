package мainPackage.repositories;

import мainPackage.models.entities.Model;
import мainPackage.models.entities.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SparePartsRepository extends JpaRepository<SparePart,Long> {
    Optional<SparePart> findBySparePartNameAndModel(String sparePartName, Model model);
}
