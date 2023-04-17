package src.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderDetails createLimitOrder(int accountId, BigDecimal priceLimit, int amount) {
        return orderRepository.save(new OrderDetails(accountId, priceLimit, amount));
    }

    public Optional<OrderDetails> fetchOrderDetails(int orderId) {
        return this.orderRepository.findById(orderId);
    }

    @Transactional
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) {
        OrderDetails order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(orderStatus);
            orderRepository.save(order);
        }
    }

    public List<OrderDetails> fetchOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.fetchOrdersByStatus(orderStatus);
    }

}
