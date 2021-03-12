package mainPackage.repositories;

import mainPackage.models.entities.Order;
import mainPackage.models.entities.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order as o where o.totalRepairPrice is null order by o.model.brand.brandName , o.model.modelName")
    List<Order> findNotReadyOrders();

    @Query("select o from Order as o where o.totalRepairPrice is not null and o.leaveDate is null order by o.model.brand.brandName , o.model.modelName , o.serialNumber")
    List<Order> findReadyOrders();

    @Query("select o from Order as o where o.client.clientName='clientName' order by o.model.brand.brandName , o.model.modelName")
    List<Order> findByClient(String clientName);

    @Query("select count(*) from Order as o JOIN o.spareParts as sp WHERE sp.id=:id")
    Long findBySparePart(@Param("id") Long id);

    List<Order> findAllByLeaveDateIsNull();
}
