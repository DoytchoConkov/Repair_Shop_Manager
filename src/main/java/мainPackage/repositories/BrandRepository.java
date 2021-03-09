package мainPackage.repositories;

import мainPackage.models.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long > {

    Optional<Brand> findByBrandName(String brand);
}
