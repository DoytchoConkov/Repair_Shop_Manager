package mainPackage.repositories;

import mainPackage.models.entities.Order;
import mainPackage.models.services.IncomePerPeriodServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order as o where o.totalRepairPrice is null order by o.model.brand.brandName , o.model.modelName")
    List<Order> findNotReadyOrders();

    @Query("select o from Order as o where o.totalRepairPrice is not null and o.leaveDate is null order by o.model.brand.brandName , o.model.modelName , o.serialNumber")
    List<Order> findReadyOrders();

    @Query("select count(o.id) from Order as o JOIN o.spareParts as sp WHERE sp.id=:id")
    Long findBySparePart(@Param("id") Long id);

    List<Order> findAllByLeaveDateIsNull();

    @Query("select o from Order as o join o.model as m join m.brand as b where o.serialNumber like :serialNumber order by b.brandName,m.modelName,o.serialNumber")
    List<Order> findAllBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("select o from Order as o join o.client as c join o.model as m join m.brand as b where c.id=:id order by b.brandName,m.modelName")
    List<Order> findAllByClientId(@Param("id") Long id);

    @Query(" select new mainPackage.models.services.IncomePerPeriodServiceModel(o.leaveDate as leaveDate,sum(o.totalSparePartsPrice) as totalSparePartsPrice,sum(o.totalRepairPrice) as totalRepairPrice) from Order as o where o.leaveDate between :startDate and :endDate group by o.leaveDate order by o.leaveDate")
    List<IncomePerPeriodServiceModel> findAllByLeaveDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
