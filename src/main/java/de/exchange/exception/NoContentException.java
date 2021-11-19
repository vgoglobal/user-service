package de.exchange.exception;

public class NoContentException extends RuntimeException {
    public NoContentException() {
        super();
    }

    public NoContentException(String errorMessage) {
        super(errorMessage);
    }
}
