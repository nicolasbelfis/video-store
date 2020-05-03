package rental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rental.exceptions.InvalidReturnDate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RentalAggregateTest {

    public static final String FILM_ID = "IH34E";
    public static final String CUSTOMER_ID = "CUST1";
    private static final LocalDate INIT_RENTAL_DATE = LocalDate.now();
    public static final int NUMBER_OF_DAYS = 5;
    @InjectMocks
    private OpenedRentalAggregate.Builder openedRentalAggregateBuilder = OpenedRentalAggregate
            .builder();
    @Mock
    private RentalPriceRule rentalPriceRule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculate_price_with_rules() {
        when(rentalPriceRule.calculatePrice(new NumberOfDays(NUMBER_OF_DAYS))).thenReturn(BigDecimal.TEN);
        assertEquals(
                BigDecimal.TEN,
                openedRentalAggregateBuilder
                        .withNumberOfDays(NUMBER_OF_DAYS)
                        .build().calculatePrice());
    }

    @Test
    public void given_return_date_before_start_cannot_close() {
        Assertions.assertThrows(
                InvalidReturnDate.class,
                () ->
                        openedRentalAggregateBuilder.withInitialDate(INIT_RENTAL_DATE).build()
                                .closeRental(INIT_RENTAL_DATE.minusDays(10))
        );
    }

    @Test
    public void when_return_date_after_start_rental_is_closed() throws InvalidReturnDate {
        when(rentalPriceRule.calculatePrice(new NumberOfDays(10))).thenReturn(BigDecimal.valueOf(20));

        ClosedRentalAggregate closedRentalAggregate = this.openedRentalAggregateBuilder
                .withInitialDate(INIT_RENTAL_DATE)
                .build()
                .closeRental(INIT_RENTAL_DATE.plusDays(10));

        assertEquals(INIT_RENTAL_DATE.plusDays(10), closedRentalAggregate.getReturnDate());
        assertEquals(BigDecimal.valueOf(20), closedRentalAggregate.calculatePrice());
    }

    @Test
    public void test_creation_with_builder() {
        OpenedRentalAggregate openedRentalAggregate = OpenedRentalAggregate
                .builder()
                .withFilmId(FILM_ID)
                .withPriceRules(FilmTypeEnum.NEW_RELEASE, SubscriptionTypeEnum.BASIC)
                .withCustomerId(CUSTOMER_ID)
                .withInitialDate(INIT_RENTAL_DATE)
                .withNumberOfDays(NUMBER_OF_DAYS)
                .build();
        assertEquals(FILM_ID, openedRentalAggregate.getFilmId());
        assertEquals(
                new RentalPriceRule(
                        FilmTypeEnum.NEW_RELEASE.getFilmTypePricing(),
                        SubscriptionTypeEnum.BASIC.getPrice()
                ), openedRentalAggregate.getPriceRule());
        assertEquals(CUSTOMER_ID, openedRentalAggregate.getCustomerId());
    }

    @Test
    public void test_closed_rental_informations() {
        ClosedRentalAggregate closedRentalAggregate = new ClosedRentalAggregate(
                OpenedRentalAggregate
                        .builder()
                        .withFilmId(FILM_ID)
                        .withPriceRules(FilmTypeEnum.NEW_RELEASE, SubscriptionTypeEnum.BASIC)
                        .withCustomerId(CUSTOMER_ID)
                        .withInitialDate(INIT_RENTAL_DATE)
                        .withNumberOfDays(NUMBER_OF_DAYS)
                        .build(),
                new NumberOfDays(10),
                INIT_RENTAL_DATE.plusDays(10)
        );

        assertEquals(CUSTOMER_ID, closedRentalAggregate.getCustomerId());
        assertEquals(INIT_RENTAL_DATE.plusDays(10), closedRentalAggregate.getReturnDate());
        assertEquals(FILM_ID, closedRentalAggregate.getFilmId());
    }
}