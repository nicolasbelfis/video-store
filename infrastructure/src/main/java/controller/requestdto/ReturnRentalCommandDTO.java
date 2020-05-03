package controller.requestdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import command.ReturnRentalCommand;
import controller.exceptions.WrongInputInRentalCreation;

import java.time.LocalDate;

public class ReturnRentalCommandDTO {
    @JsonCreator
    public ReturnRentalCommandDTO(
            @JsonProperty(value = "closeDate", required = true) LocalDate closeDate,
            @JsonProperty(value = "customerId", required = true) String customerId,
            @JsonProperty(value = "filmId", required = true) String filmId
    ) {
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

    private final LocalDate closeDate;
    private final String customerId;

    private final String filmId;

    public ReturnRentalCommand toCommand() {
        try {
            return new ReturnRentalCommand(
                    closeDate,
                    customerId,
                    filmId);
        } catch (IllegalArgumentException e) {
            throw new WrongInputInRentalCreation(e);
        }
    }

}
