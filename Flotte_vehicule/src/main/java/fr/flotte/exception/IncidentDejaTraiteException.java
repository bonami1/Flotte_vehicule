package fr.flotte.exception;

public class IncidentDejaTraiteException extends Exception {
    public IncidentDejaTraiteException(String id) {
        super("L'incident " + id + " est deja marque comme traite.");
    }
}