package customer;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerAggregateTest {

    public static final String CUSTOMER_ID = "1234";

    @Test
    public void when_new_release_added_increment_bonus() {
        CustomerAggregate customerAggregate = aCustomerWithBonus(BigInteger.valueOf(2));
        assertEquals(BigInteger.valueOf(4), customerAggregate.addBonusFromFilm(FilmTypeBonus.NEW_RELEASE).getBonus());
    }

    @Test
    public void when_other_film_added_increment_bonus() {
        CustomerAggregate customerAggregate = aCustomerWithBonus(BigInteger.valueOf(2));
        assertEquals(BigInteger.valueOf(3), customerAggregate.addBonusFromFilm(FilmTypeBonus.OTHER).getBonus());
    }

    private CustomerAggregate aCustomerWithBonus(BigInteger bigInteger) {
        return new CustomerAggregate(CUSTOMER_ID, bigInteger);
    }
}