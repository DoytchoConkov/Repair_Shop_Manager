package mainPackage.repositories;

import mainPackage.models.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    @Query("select m from ModelEntity as m where m.modelName=:model and m.brand.brandName=:brand")
    ModelEntity findByModelName(String brand, String model);

    @Query("select m.modelName from ModelEntity as m where m.brand.brandName=:brandName group by m.modelName order by m.modelName")
    List<String> getByBrandName(String brandName);
}
