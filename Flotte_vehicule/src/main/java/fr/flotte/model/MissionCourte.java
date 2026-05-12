package fr.flotte.model;

public class MissionCourte extends Mission {

    public MissionCourte(String itineraire) {
        super(itineraire); // On envoie l'itinéraire à la classe parente (Mission)
    }

    @Override
    public String genererRapport() {
        return "Mission courte terminée : trajet local effectué sur " + getItineraire();
    }
}