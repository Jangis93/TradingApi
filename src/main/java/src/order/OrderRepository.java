package src.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderDetails, Integer> {

    @Query(value = "FROM OrderDetails od WHERE od.status = ?1")
    List<OrderDetails> fetchOrdersByStatus(OrderStatus orderStatus);

    @Query(value="UPDATE OrderDetails od SET od.status = ?1 WHERE od.orderId = ?2")
    void updateStatus(OrderStatus orderStatus, int orderId);

}
