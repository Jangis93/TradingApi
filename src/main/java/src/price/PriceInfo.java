package src.price;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PriceInfo {

    @JsonProperty("price")
    private BigDecimal price;

    // TODO: verify timestamp
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    public BigDecimal getPrice() {
        return price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
