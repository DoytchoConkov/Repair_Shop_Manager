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
    @Query("select m from Model as m where m.modelName=:model and m.brand.brandName=:brand")
    Model findByModelName(String brand,String model);
}
