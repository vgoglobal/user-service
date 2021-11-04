package de.hey_car.exception;

public class NoContentException extends RuntimeException {
    public NoContentException() {
        super();
    }

    public NoContentException(String errorMessage) {
        super(errorMessage);
    }
}
