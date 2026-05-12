package fr.flotte.exception;

public class VehiculeException extends Exception {
    public VehiculeException(String message) {
        super(message);
    }

    public VehiculeException(String message, Throwable cause) {
        super(message, cause);
    }
}
