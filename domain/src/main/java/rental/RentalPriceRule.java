package rental;

import java.math.BigDecimal;

class RentalPriceRule {
    private final FilmTypePricing filmTypePricing;
    private final BigDecimal subscriptionPrice;

    public RentalPriceRule(FilmTypePricing filmTypePricing, BigDecimal subscriptionPrice) {
        this.filmTypePricing = filmTypePricing;
        this.subscriptionPrice = subscriptionPrice;
    }

    BigDecimal calculatePrice(NumberOfDays numberOfDays) {
        return filmTypePricing.calculatePrice(numberOfDays, subscriptionPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalPriceRule that = (RentalPriceRule) o;

        if (filmTypePricing != null ? !filmTypePricing.equals(that.filmTypePricing) : that.filmTypePricing != null)
            return false;
        return subscriptionPrice != null ? subscriptionPrice.equals(that.subscriptionPrice) : that.subscriptionPrice == null;
    }

    @Override
    public int hashCode() {
        int result = filmTypePricing != null ? filmTypePricing.hashCode() : 0;
        result = 31 * result + (subscriptionPrice != null ? subscriptionPrice.hashCode() : 0);
        return result;
    }
}