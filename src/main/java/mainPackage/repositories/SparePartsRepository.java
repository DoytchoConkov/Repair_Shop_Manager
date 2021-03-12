package mainPackage.repositories;

import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SparePartsRepository extends JpaRepository<SparePart, Long> {
    Optional<SparePart> findBySparePartNameAndModel(String sparePartName, Model model);
@Query("select sp from SparePart as sp JOIN sp.model as m JOIN m.brand as b where b.brandName=:brand and m.modelName=:model")
    List<SparePart> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);
}
