package rental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClosedRentalAggregate {
    private final OpenedRentalAggregate openedRentalAggregate;

    private final NumberOfDays numberOfDays;

    private final LocalDate returnDate;

    ClosedRentalAggregate(OpenedRentalAggregate openedRentalAggregate, NumberOfDays numberOfDays, LocalDate returnDate) {

        this.openedRentalAggregate = openedRentalAggregate;
        this.numberOfDays = numberOfDays;
        this.returnDate = returnDate;
    }

    public long getNumberOfDays() {
        return numberOfDays.getNumberOfDays();
    }

    public BigDecimal calculatePrice() {
        return openedRentalAggregate.getPriceRule().calculatePrice(numberOfDays);
    }

    public String getFilmId() {
        return openedRentalAggregate.getFilmId();
    }

    public String getCustomerId() {
        return openedRentalAggregate.getCustomerId();
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
