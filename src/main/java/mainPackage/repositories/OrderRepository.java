package mainPackage.repositories;

import mainPackage.models.entities.OrderEntity;
import mainPackage.models.services.IncomePerPeriodServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select o from OrderEntity as o where o.totalRepairPrice is null order by o.model.brand.brandName , o.model.modelName")
    List<OrderEntity> findNotReadyOrders();

    @Query("select o from OrderEntity as o where o.totalRepairPrice is not null and o.leaveDate is null order by o.model.brand.brandName , o.model.modelName , o.serialNumber")
    List<OrderEntity> findReadyOrders();

    @Query("select count(o.id) from OrderEntity as o JOIN o.spareParts as sp WHERE sp.id=:id")
    Long findBySparePart(@Param("id") Long id);

    List<OrderEntity> findAllByLeaveDateIsNull();

    @Query("select o from OrderEntity as o join o.model as m join m.brand as b where o.serialNumber like :serialNumber and o.leaveDate is not null order by b.brandName,m.modelName,o.serialNumber")
    List<OrderEntity> findAllBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("select o from OrderEntity as o join o.client as c join o.model as m join m.brand as b where c.id=:id order by b.brandName,m.modelName")
    List<OrderEntity> findAllByClientId(@Param("id") Long id);

    @Query(" select new mainPackage.models.services.IncomePerPeriodServiceModel(o.leaveDate,sum(o.totalSparePartsPrice) ,sum(o.totalRepairPrice) ) from OrderEntity as o where o.leaveDate between :startDate and :endDate group by o.leaveDate order by o.leaveDate")
    List<IncomePerPeriodServiceModel> findAllByLeaveDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("select o from OrderEntity as o where o.leaveDate = :date order by o.leaveDate")
    List<OrderEntity> getByDate(@Param("date") LocalDate date);

    @Query(" select new mainPackage.models.services.IncomePerPeriodServiceModel(o.leaveDate ,sum(o.totalSparePartsPrice) ,sum(o.totalRepairPrice)) from OrderEntity as o where o.user.username=:technician and o.leaveDate between :startDate and :endDate group by o.leaveDate order by o.leaveDate")
    List<IncomePerPeriodServiceModel> findAllByLeaveDateBetweenAndTechnician(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDat, @Param("technician") String technician);

    @Query("select o.user.username from OrderEntity as o where o.user.username is not NULL group by o.user.username order by o.user.username")
    List<String> findTechnicians();

    @Query("select count(o.id) from OrderEntity as o where o.totalRepairPrice is null")
    int countNotReadyOrders();

    @Query("select count(o.id) from OrderEntity as o where o.totalRepairPrice is not null and o.leaveDate is null")
    int countReadyOrders();
}
