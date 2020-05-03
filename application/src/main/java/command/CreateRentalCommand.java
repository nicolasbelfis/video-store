package command;

import rental.FilmTypeEnum;
import rental.SubscriptionTypeEnum;

import java.time.LocalDate;

public class CreateRentalCommand {
    private final long numberOfDays;
    private final LocalDate initalDate;
    private final String customerId;
    private final String filmId;
    private final FilmTypeEnum filmType;
    private final SubscriptionTypeEnum subscriptionType;

    public CreateRentalCommand(long numberOfDays, LocalDate initalDate, String customerId, String filmId, FilmTypeEnum filmType, SubscriptionTypeEnum subscriptionType) {
        this.numberOfDays = numberOfDays;
        this.initalDate = initalDate;
        this.customerId = customerId;
        this.filmId = filmId;
        this.filmType = filmType;
        this.subscriptionType = subscriptionType;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public LocalDate getInitalDate() {
        return initalDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFilmId() {
        return filmId;
    }


    public FilmTypeEnum getFilmType() {
        return filmType;
    }

    public SubscriptionTypeEnum getSubscriptionType() {
        return subscriptionType;
    }

}
