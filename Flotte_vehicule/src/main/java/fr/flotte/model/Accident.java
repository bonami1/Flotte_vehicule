package fr.flotte.model;

public class Accident extends Incident {
    private static final long serialVersionUID = 1L;

    private String gravite;

    public Accident(String description, double coutEstime, String vehiculeId, String gravite) {
        super(description, coutEstime, vehiculeId);
        this.gravite = gravite;
    }

    @Override
    public String genererRapport() {
        return "Accident (" + gravite + ") : " + getDescription()
                + " | Cout estime : " + getCoutEstime() + " EUR";
    }

    @Override
    public String getType() { return "Accident"; }

    public String getGravite() { return gravite; }
    public void setGravite(String gravite) { this.gravite = gravite; }
}