package mainPackage.repositories;

import mainPackage.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order as o where o.totalRepairPrice is null order by o.model.brand.brandName , o.model.modelName")
    List<Order> findNotReadyOrders();

    @Query("select o from Order as o where o.totalRepairPrice is not null and o.leaveDate is null order by o.model.brand.brandName , o.model.modelName , o.serialNumber")
    List<Order> findReadyOrders();

    @Query("select o from Order as o where o.client.clientName=:clientName order by o.model.brand.brandName , o.model.modelName")
    List<Order> findByClient(@Param("clientName") String clientName);

    @Query("select count(o.id) from Order as o JOIN o.spareParts as sp WHERE sp.id=:id")
    Long findBySparePart(@Param("id") Long id);

    List<Order> findAllByLeaveDateIsNull();

    @Query("select o from Order as o where o.serialNumber like :serialNumber ")
    List<Order> findAllBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("select o from Order as o join o.client as c join o.model as m join m.brand as b where c.id=:id order by b.brandName,m.modelName")
    List<Order> findAllByClientId(@Param("id") Long id);

    List<Order> findAllByLeaveDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
