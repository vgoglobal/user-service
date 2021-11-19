package de.exchange.exception;

public class CustomDBException extends RuntimeException {
    public CustomDBException(){
        super();
    }
    public CustomDBException(String errorMessage){
        super(errorMessage);
    }
}
