package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalDTO {
    private String filmId;
    private String customerId;
    private BigDecimal price;
    private LocalDate startDate;
    private long numberOfDays;
    private BigDecimal overCharge;
    private LocalDate returnDate;

    public String getFilmId() {
        return filmId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public RentalDTO(String filmId, String customerId, BigDecimal price, LocalDate startDate, long numberOfDays) {
        this.filmId = filmId;
        this.customerId = customerId;
        this.price = price;
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
    }

    public RentalDTO() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public void setOverCharge(BigDecimal overCharge) {
        this.overCharge = overCharge;
    }

    public BigDecimal getOverCharge() {
        return overCharge;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setNumberOfDays(long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
