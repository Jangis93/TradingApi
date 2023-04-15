package src.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public int createLimitOrder(int accountId, BigDecimal priceLimit, int amount) {
        return orderRepository.save(new OrderDetails(accountId, priceLimit, amount)).getAccountId();
    }
    // can the order be executed immediately?

    public Optional<OrderDetails> fetchOrderDetails(int orderId) {
        return this.orderRepository.findById(orderId);
    }

    public void executeOrder(int orderId) {
        orderRepository.updateStatus(OrderStatus.PROCESSED, orderId);
    }

    public List<OrderDetails> fetchNonProcessedOrders() {
        return orderRepository.findNonProcessedOrders(OrderStatus.CREATED.name());
    }

}
