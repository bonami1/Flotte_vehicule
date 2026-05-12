package fr.flotte.exception;

public class ChauffeurIndisponibleException extends Exception {
    public ChauffeurIndisponibleException(String nomChauffeur) {
        super("Le chauffeur " + nomChauffeur + " n'est pas disponible.");
    }
}