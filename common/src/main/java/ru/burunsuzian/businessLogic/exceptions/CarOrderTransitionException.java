package ru.burunsuzian.businessLogic.exceptions;

public class CarOrderTransitionException extends RuntimeException {
    public CarOrderTransitionException(String message) {
        super(message);
    }
}
