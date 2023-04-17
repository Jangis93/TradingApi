package src.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "orderDetails")
@Entity
@NoArgsConstructor
@Getter
@Setter
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
}
