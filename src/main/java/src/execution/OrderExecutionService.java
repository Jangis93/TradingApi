package src.execution;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.account.AccountDetails;
import src.account.AccountService;
import src.order.OrderDetails;
import src.order.OrderService;
import src.order.OrderStatus;
import src.price.PriceInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class OrderExecutionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    public void handleOrders(PriceInfo newPrice) {
        List<OrderDetails> orders = orderService.fetchOrdersByStatus(OrderStatus.CREATED);

        orders.stream()
                .filter(c -> c.getPriceLimit().compareTo(newPrice.getPrice()) >= 0)
                .forEach(order ->
                    orderService.updateOrderStatus(order.getOrderId(), OrderStatus.PENDING)
                );

        List<OrderDetails> pendingOrders = orderService.fetchOrdersByStatus(OrderStatus.PENDING);
        pendingOrders.forEach(order -> {
            try {
                executeOrder(order);
            } catch(NoSuchElementException | InsufficientFundsException e) {
                log.error(String.format("Failed to execute order for account: %d", order.getAccountId()), e);
                orderService.updateOrderStatus(order.getOrderId(), OrderStatus.FAILED);
            }
        });
    }

    @Transactional
    public void executeOrder(OrderDetails order) throws NoSuchElementException, InsufficientFundsException {
        AccountDetails accountDetails = accountService.fetchAccountDetails(order.getAccountId()).orElseThrow();
        BigDecimal currentBalance = accountDetails.getBalance();
        BigDecimal totalOrderPrice = getOrderPrice(order);

        if(currentBalance.compareTo(totalOrderPrice) > 0) {
            accountDetails.setBalance(currentBalance.subtract(totalOrderPrice));
            accountService.updateAccount(accountDetails);
            orderService.updateOrderStatus(order.getOrderId(), OrderStatus.PROCESSED);
        } else {
            throw new InsufficientFundsException(
                    String.format("Customer %d does not have enough funds on their account", accountDetails.getId())
            );
        }

    }

    private BigDecimal getOrderPrice(OrderDetails order) {
        return order.getPriceLimit().multiply(BigDecimal.valueOf(order.getAmount()));
    }

}
