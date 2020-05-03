package application.exceptions;

public class InternalApplicationException extends RuntimeException {
    public InternalApplicationException(RuntimeException e) {
        super(e);
    }
}
