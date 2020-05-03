package application.exceptions;

public class UnvalidParameterException extends RuntimeException {
    public UnvalidParameterException(RuntimeException e) {
        super(e);
    }
}
