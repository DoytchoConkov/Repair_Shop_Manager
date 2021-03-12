package mainPackage.repositories;

import mainPackage.models.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandName(String brand);

    @Query("select b from Brand as b JOIN b.models as m order by b.brandName,m.modelName")
    List<Brand> findAll();
}
