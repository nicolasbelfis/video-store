package controller.requestdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import command.CreateRentalCommand;
import controller.exceptions.WrongInputInRentalCreation;
import rental.FilmTypeEnum;
import rental.SubscriptionTypeEnum;

import java.time.LocalDate;

public class CreateRentalCommandDTO {
    public int getNumberOfDays() {
        return numberOfDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFilmId() {
        return filmId;
    }

    public String getFilmType() {
        return filmType;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    private final int numberOfDays;
    private final LocalDate startDate;
    private final String customerId;
    private final String filmId;
    private final String filmType;
    private final String subscriptionType;

    @JsonCreator
    public CreateRentalCommandDTO(
            @JsonProperty(value = "numberOfDays", required = true) int numberOfDays,
            @JsonProperty(value = "startDate", required = true) LocalDate startDate,
            @JsonProperty(value = "customerId", required = true) String customerId,
            @JsonProperty(value = "filmId", required = true) String filmId,
            @JsonProperty(value = "filmType", required = true) String filmType,
            @JsonProperty(value = "subscriptionType", required = true) String subscriptionType) {
        this.numberOfDays = numberOfDays;
        this.startDate = startDate;
        this.customerId = customerId;
        this.filmId = filmId;
        this.filmType = filmType;
        this.subscriptionType = subscriptionType;
    }

    public CreateRentalCommand toCommand() {
        try {
            return new CreateRentalCommand(
                    numberOfDays,
                    startDate,
                    customerId,
                    filmId,
                    FilmTypeEnum.valueOf(filmType),
                    SubscriptionTypeEnum.valueOf(subscriptionType));
        } catch (IllegalArgumentException e) {
            throw new WrongInputInRentalCreation(e);
        }
    }

}
