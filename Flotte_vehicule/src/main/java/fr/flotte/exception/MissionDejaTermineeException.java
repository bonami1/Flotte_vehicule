package fr.flotte.exception;

public class MissionDejaTermineeException extends Exception {
    public MissionDejaTermineeException(String idMission) {
        super("La mission " + idMission + " est déjà terminée.");
    }
}