package src.price;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfo implements Serializable {

    @JsonProperty("price")
    @NonNull
    private BigDecimal price;

    @JsonProperty("timestamp")
    @NonNull
    private Timestamp timestamp;

}
