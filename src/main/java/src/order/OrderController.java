package src.order;

import com.hazelcast.internal.ascii.rest.HttpStatusCode;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.DefaultUriBuilderFactory;
import src.account.AccountDetails;
import src.account.AccountService;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/order")
@Slf4j
@AllArgsConstructor
public class OrderController {

    @NonNull
    private OrderService orderService;

    @NonNull
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<OrderDetails> createLimitOrder(@RequestParam int accountId, @RequestParam BigDecimal priceLimit, @RequestParam int amount) {
        if(accountService.fetchAccountDetails(accountId).isPresent()) {
            log.info(String.format("Received request to create new order with accountId %d, priceLimit %.5f and amount %d", accountId, priceLimit, amount));
            OrderDetails newOrder = this.orderService.createLimitOrder(accountId, priceLimit, amount);
            return ResponseEntity.created(URI.create(("/order/create"))).body(newOrder);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetails> fetchOrderDetails(@PathVariable Integer orderId) {
        log.info(String.format("Fetching order details with orderId: %d", orderId));
        OrderDetails order = this.orderService.fetchOrderDetails(orderId).orElse(null);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }
}
