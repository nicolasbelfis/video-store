package command;

import java.time.LocalDate;

public class ReturnRentalCommand {
    private final LocalDate closeDate;
    private final String customerId;
    private final String filmId;

    public ReturnRentalCommand(LocalDate closeDate, String customerId, String filmId) {
        this.closeDate = closeDate;
        this.customerId = customerId;
        this.filmId = filmId;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFilmId() {
        return filmId;
    }

}
