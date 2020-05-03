package rental;

import rental.exceptions.InvalidReturnDate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OpenedRentalAggregate {
    private final String filmId;
    private final String customerId;
    private final RentalPriceRule priceRule;
    private final LocalDate initialDate;
    private final NumberOfDays numberOfDays;

    private OpenedRentalAggregate(Builder builder) {
        this.filmId = builder.filmId;
        this.customerId = builder.customerId;
        this.initialDate = builder.initialDate;
        this.numberOfDays = new NumberOfDays(builder.numberOfDays);
        this.priceRule = builder.priceRule;
    }

    public ClosedRentalAggregate closeRental(LocalDate returnDate) throws InvalidReturnDate {
        if (returnDate.isBefore(initialDate)) throw new InvalidReturnDate();
        NumberOfDays.between(returnDate, initialDate);

        return new ClosedRentalAggregate(this, NumberOfDays.between(returnDate, initialDate), returnDate);
    }

    public RentalPriceRule getPriceRule() {
        return priceRule;
    }

    public BigDecimal calculatePrice() {
        return priceRule.calculatePrice(numberOfDays);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFilmId() {
        return filmId;
    }

    public String getCustomerId() {
        return customerId;
    }


    public static class Builder {
        public RentalPriceRule priceRule;
        private String filmId;
        private String customerId;
        private long numberOfDays;
        private LocalDate initialDate;

        public Builder() {
        }

        public Builder withFilmId(String filmId) {
            this.filmId = filmId;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withPriceRules(FilmTypeEnum filmType, SubscriptionTypeEnum subscriptionType) {
            this.priceRule = new RentalPriceRule(
                    filmType.getFilmTypePricing(),
                    subscriptionType.getPrice()
            );
            return this;
        }

        public Builder withNumberOfDays(long numberOfDays) {
            this.numberOfDays = numberOfDays;
            return this;
        }

        public Builder withInitialDate(LocalDate initialDate) {
            if (initialDate.isBefore(LocalDate.now()))
                throw new IllegalArgumentException("wrong date - must be at least 1 day after today");
            this.initialDate = initialDate;
            return this;
        }


        public OpenedRentalAggregate build() {

            return new OpenedRentalAggregate(this);
        }
    }
}
