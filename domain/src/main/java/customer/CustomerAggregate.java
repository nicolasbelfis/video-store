package customer;

import java.math.BigInteger;

public class CustomerAggregate {

    private final String customerId;

    private BigInteger bonus;

    public CustomerAggregate(String customerId, BigInteger bonus) {
        this.customerId = customerId;
        this.bonus = bonus;
    }

    public CustomerAggregate addBonusFromFilm(FilmTypeBonus filmTypeBonus) {
        bonus = filmTypeBonus.getBonus().add(bonus);
        return this;
    }

    public BigInteger getBonus() {
        return bonus;
    }

    public String getCustomerId() {
        return customerId;
    }
}
