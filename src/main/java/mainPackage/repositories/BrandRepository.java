package mainPackage.repositories;

import mainPackage.models.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    BrandEntity findByBrandName(String brand);

    @Query("select b from BrandEntity as b order by b.brandName")
    List<BrandEntity> findAll();
}
