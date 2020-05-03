package rental;

import java.math.BigDecimal;

interface FilmTypePricing {

    BigDecimal calculatePrice(NumberOfDays days, BigDecimal price);
}
