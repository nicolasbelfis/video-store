package rental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class NumberOfDays {

    private final long numberOfDays;

    public NumberOfDays(long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public static NumberOfDays between(LocalDate returnDate, LocalDate initialDate) {
        return new NumberOfDays(ChronoUnit.DAYS.between(initialDate, returnDate));
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public NumberOfDays minus(long i) {
        return new NumberOfDays(numberOfDays - i);
    }

    public NumberOfDays orZero() {
        if (numberOfDays >= 1) return new NumberOfDays(numberOfDays);
        return new NumberOfDays(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberOfDays that = (NumberOfDays) o;

        return numberOfDays == that.numberOfDays;
    }

    @Override
    public int hashCode() {
        return (int) (numberOfDays ^ (numberOfDays >>> 32));
    }
}
