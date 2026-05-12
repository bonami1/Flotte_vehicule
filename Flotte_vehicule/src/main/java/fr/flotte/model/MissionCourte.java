package fr.flotte.model;

public class MissionCourte extends Mission {
    private static final long serialVersionUID = 1L;

    public MissionCourte(String itineraire) {
        super(itineraire);
    }

    @Override
    public String genererRapport() {
        return "Mission courte terminee : trajet local effectue sur " + getItineraire();
    }

    @Override
    public String getType() { return "Courte"; }
}