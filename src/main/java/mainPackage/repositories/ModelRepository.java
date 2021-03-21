package mainPackage.repositories;

import mainPackage.models.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByModelName(String name);

    @Query("select m.modelName from Model as m where m.brand.brandName=:brandName order by m.modelName")
    List<String> findByBrandName(@Param("brandName") String brandName);
}
