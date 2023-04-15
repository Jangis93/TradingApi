package src.order;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @NonNull
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    public int createLimitOrder(@RequestParam int accountId, @RequestParam BigDecimal priceLimit, @RequestParam int amount) {
        logger.info(String.format("Received request to create new order with accountId %d, priceLimit %.5f and amount %d", accountId, priceLimit, amount));
        return this.orderService.createLimitOrder(accountId, priceLimit, amount);
    }

    @GetMapping("/{orderId}")
    public Optional<OrderDetails> fetchOrderDetails(@PathVariable Integer orderId) {
        logger.info(String.format("Fetching order details with orderId: %d", orderId));
        return this.orderService.fetchOrderDetails(orderId);
    }
}
