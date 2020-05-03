package rental;

import java.math.BigDecimal;

public enum SubscriptionTypeEnum {

    PREMIUM(BigDecimal.valueOf(1)),
    BASIC(BigDecimal.valueOf(3));

    private final BigDecimal price;

    SubscriptionTypeEnum(BigDecimal price) {

        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
