package mainPackage.repositories;

import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SparePartsRepository extends JpaRepository<SparePart,Long> {
    Optional<SparePart> findBySparePartNameAndModel(String sparePartName, Model model);
}
