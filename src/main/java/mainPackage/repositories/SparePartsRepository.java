package mainPackage.repositories;

import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import mainPackage.models.views.SparePartViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SparePartsRepository extends JpaRepository<SparePart, Long> {
    Optional<SparePart> findBySparePartNameAndModel(String sparePartName, Model model);

    @Query("select sp from SparePart as sp JOIN sp.model as m JOIN m.brand as b where b.brandName=:brand and m.modelName=:model")
    List<SparePart> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);

    @Query("select distinct sp.model.modelName from SparePart as sp join sp.model as m join m.brand as b where b.brandName=:brandName")
    List<String> getModelsByBrandName(@Param("brandName") String brandName);

    @Query("select sp from SparePart as sp join sp.model as m join m.brand as b where m.modelName=:modelName and b.brandName=:brandName")
    List<SparePart> getSparePartsByBrandNameAndModel(String brandName, String modelName);


    @Query("select sp  from SparePart as sp join sp.model as m join m.brand as b where b.brandName=:brandName and sp.pieces>0")
    List<SparePart> getSparePartsBrandName(String brandName);

    @Query("select sum(sp.price) from SparePart as sp where sp.id in :sparePartsId ")
    BigDecimal getTotalPrice(@Param("sparePartsId") Long[] sparePartsId);

    @Query("select sp from SparePart as sp where sp.model.brand.brandName=:brandName and sp.model.modelName=:modelName and sp.sparePartName=:spName")
    SparePart getByBrandModelName(String brandName, String modelName, String spName);
}
