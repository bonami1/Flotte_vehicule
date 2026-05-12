package fr.flotte.model;

public class MissionLongue extends Mission implements Trackable {
    private static final long serialVersionUID = 1L;
    private int nombreDePauses;
    private String positionActuelle;

    public MissionLongue(String itineraire, int nombreDePauses) {
        super(itineraire);
        this.nombreDePauses = nombreDePauses;
        this.positionActuelle = "Depart";
    }

    @Override
    public String genererRapport() {
        return "Mission longue vers " + getItineraire() + " avec " + nombreDePauses
                + " pauses. Position : " + positionActuelle;
    }

    @Override
    public String getType() { return "Longue"; }

    @Override
    public String getSuiviItineraire() {
        return getItineraire() + " -> " + positionActuelle;
    }

    @Override
    public void mettreAJourPosition(String position) {
        this.positionActuelle = position;
    }

    public int getNombreDePauses() { return nombreDePauses; }
    public String getPositionActuelle() { return positionActuelle; }
}