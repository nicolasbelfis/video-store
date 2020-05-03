package spi.messaging.event;

import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalClosedEvent implements Event {

    public RentalClosedEvent(String customerId, String filmId, LocalDate returnDate, BigDecimal overCharge, long numberOfDays) {
        this.customerId = customerId;
        this.filmId = filmId;
        this.returnDate = returnDate;
        this.overCharge = overCharge;
        this.numberOfDays = numberOfDays;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFilmId() {
        return filmId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BigDecimal getOverCharge() {
        return overCharge;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    private final String customerId;
    private final String filmId;
    private final LocalDate returnDate;
    private final BigDecimal overCharge;

    private final long numberOfDays;

    @Override
    public Mono<Void> accept(EventSubscriber eventSubscriber) {
        return eventSubscriber.consume(this);
    }
}
