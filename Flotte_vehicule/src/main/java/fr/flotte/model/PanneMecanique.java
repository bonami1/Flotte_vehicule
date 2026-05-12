package fr.flotte.model;

public class PanneMecanique extends Incident {
    private static final long serialVersionUID = 1L;

    private String typePanne;

    public PanneMecanique(String description, double coutEstime, String vehiculeId, String typePanne) {
        super(description, coutEstime, vehiculeId);
        this.typePanne = typePanne;
    }

    @Override
    public String genererRapport() {
        return "Panne mecanique (" + typePanne + ") : " + getDescription()
                + " | Cout estime : " + getCoutEstime() + " EUR";
    }

    @Override
    public String getType() { return "Panne"; }

    public String getTypePanne() { return typePanne; }
    public void setTypePanne(String typePanne) { this.typePanne = typePanne; }
}