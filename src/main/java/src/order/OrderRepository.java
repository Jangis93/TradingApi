package src.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderDetails, Integer> {

    @Query(value = "FROM OrderDetails od WHERE od.status = ?1")
    List<OrderDetails> findNonProcessedOrders(String orderStatus);

    @Query(value="UPDATE OrderDetails od SET status = ?1 WHERE orderId = ?2")
    boolean updateStatus(OrderStatus orderStatus, int orderId);

}
