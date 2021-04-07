package mainPackage.repositories;

import mainPackage.models.entities.ModelEntity;
import mainPackage.models.entities.SparePartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SparePartsRepository extends JpaRepository<SparePartEntity, Long> {
    Optional<SparePartEntity> findBySparePartNameAndModel(String sparePartName, ModelEntity model);

    @Query("select sp from SparePartEntity as sp JOIN sp.model as m JOIN m.brand as b where b.brandName=:brand and m.modelName=:model")
    List<SparePartEntity> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);

    @Query("select sp from SparePartEntity as sp join sp.model as m join m.brand as b where m.modelName=:modelName and b.brandName=:brandName and sp.pieces>0")
    List<SparePartEntity> getSparePartsByBrandNameAndModel(String brandName, String modelName);


    @Query("select sp  from SparePartEntity as sp join sp.model as m join m.brand as b where b.brandName=:brandName and sp.pieces>0")
    List<SparePartEntity> getSparePartsBrandName(String brandName);

    @Query("select sum(sp.price) from SparePartEntity as sp where sp.id in :sparePartsId ")
    BigDecimal getTotalPrice(@Param("sparePartsId") Long[] sparePartsId);

    @Query("select sp from SparePartEntity as sp where sp.model.brand.brandName=:brandName and sp.model.modelName=:modelName and sp.sparePartName=:spName")
    SparePartEntity getByBrandModelName(String brandName, String modelName, String spName);
}
