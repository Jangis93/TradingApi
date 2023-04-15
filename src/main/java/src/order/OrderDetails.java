package src.order;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "orderDetails")
@Entity
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue
    @Column
    private int orderId;

    @Column
    private int accountId;

    @Column
    private BigDecimal priceLimit;

    @Column
    private int amount;

    @Column
    private OrderStatus status;

    public OrderDetails(int accountId, BigDecimal priceLimit, int amount) {
        this.accountId = accountId;
        this.priceLimit = priceLimit;
        this.amount = amount;
        this.status = OrderStatus.CREATED;
    }

    public int getAccountId() {
        return accountId;
    }

    public BigDecimal getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
