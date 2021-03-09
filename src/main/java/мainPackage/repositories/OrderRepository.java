package мainPackage.repositories;

import мainPackage.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select p from Order as p where p.totalRepairPrice is null")
    List<Order> findNotReadyOrders();
    @Query("select p from Order as p where p.totalRepairPrice is not null and p.leaveDate is null")
    List<Order> findReadyOrders();
}
