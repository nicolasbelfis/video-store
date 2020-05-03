package rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PriceRuleTest {

    @InjectMocks
    private RentalPriceRule rentalPriceRule;
    @Mock
    private BigDecimal subPrice;
    @Mock
    private FilmTypePricing filmTypePricing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        rentalPriceRule = new RentalPriceRule(filmTypePricing, subPrice);
    }

    @Test
    public void calculate_price_with_rules() {
        when(filmTypePricing.calculatePrice(new NumberOfDays(5), subPrice)).thenReturn(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, rentalPriceRule.calculatePrice(new NumberOfDays(5)));
    }

}