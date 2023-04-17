package src.execution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import src.account.AccountDetails;
import src.account.AccountService;
import src.order.OrderDetails;
import src.order.OrderService;
import src.order.OrderStatus;
import src.price.PriceInfo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderExecutionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderExecutionService orderExecutionService;

    @Test
    public void shouldNotExecuteOrderDueToNoPriceMatch() {
        List<OrderDetails> orders = List.of(new OrderDetails(1, BigDecimal.valueOf(200.4332), 2));
        when(orderService.fetchOrdersByStatus(OrderStatus.CREATED)).thenReturn(orders);

        orderExecutionService.handleOrders(new PriceInfo(BigDecimal.valueOf(250.0), new Timestamp(14l)));

        verify(orderService, times(2)).fetchOrdersByStatus(any());
        verifyNoMoreInteractions(orderService);
        verifyNoInteractions(accountService);
    }

    @Test
    public void shouldNotExecuteOrderDueToInsufficientFunds() {
        OrderDetails order = new OrderDetails(0, BigDecimal.valueOf(200.4332), 2);
        OrderDetails order2 = new OrderDetails(0, BigDecimal.valueOf(200.4332), 2);
        order2.setStatus(OrderStatus.PENDING);

        when(orderService.fetchOrdersByStatus(OrderStatus.CREATED)).thenReturn(List.of(order));
        when(orderService.fetchOrdersByStatus(OrderStatus.PENDING)).thenReturn(List.of(order2));

        orderExecutionService.handleOrders(new PriceInfo(BigDecimal.valueOf(200.0), new Timestamp(14l)));

        verify(accountService, times(1)).fetchAccountDetails(anyInt());
        verify(orderService, times(2)).fetchOrdersByStatus(any());
        verify(orderService, times(2)).updateOrderStatus(anyInt(), any());
        verifyNoMoreInteractions(orderService);
        verifyNoMoreInteractions(accountService);
    }


    @Test
    public void shouldExecuteOrder() {
        OrderDetails order = new OrderDetails(1, BigDecimal.valueOf(200.4332), 2);
        OrderDetails order2 = new OrderDetails(1, BigDecimal.valueOf(200.4332), 2);
        order2.setStatus(OrderStatus.PENDING);

        when(orderService.fetchOrdersByStatus(OrderStatus.CREATED)).thenReturn(List.of(order));
        when(orderService.fetchOrdersByStatus(OrderStatus.PENDING)).thenReturn(List.of(order2));
        when(accountService.fetchAccountDetails(1)).thenReturn(java.util.Optional.of(new AccountDetails("Anna", BigDecimal.valueOf(2000))));

        orderExecutionService.handleOrders(new PriceInfo(BigDecimal.valueOf(200.0), new Timestamp(14l)));

        verify(accountService, times(1)).updateAccount(any());
        verify(orderService, times(2)).updateOrderStatus(anyInt(), any());
    }

}