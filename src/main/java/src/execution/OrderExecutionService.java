package src.execution;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.account.AccountService;
import src.order.OrderDetails;
import src.order.OrderService;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderExecutionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    /*
    1. Event based service: should be triggered when new price is available and check non processed orders
    2. Should also validate incoming new orders
     */


    // 1.
    public void executeOrders(List<OrderDetails> orders) {

    }

    public void validateOrder(OrderDetails order) {
        
    }

}
