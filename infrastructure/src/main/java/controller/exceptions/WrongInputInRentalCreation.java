package controller.exceptions;

public class WrongInputInRentalCreation extends RuntimeException {
    public WrongInputInRentalCreation(IllegalArgumentException e) {
        super(e);
    }
}
