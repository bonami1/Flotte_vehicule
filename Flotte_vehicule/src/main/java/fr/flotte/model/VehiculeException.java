package fr.flotte.model;

public class VehiculeException extends Exception {
    public VehiculeException(String message) {
        super(message);
    }

    public VehiculeException(String message, Throwable cause) {
        super(message, cause);
    }
}
