package fr.flotte.model;

public class MissionLongue extends Mission {
    private int nombreDePauses;

    public MissionLongue(String itineraire, int nombreDePauses) {
        super(itineraire);
        this.nombreDePauses = nombreDePauses;
    }

    @Override
    public String genererRapport() {
        return "Mission longue terminée : trajet vers " + getItineraire() + " avec " + nombreDePauses + " pauses.";
    }
}