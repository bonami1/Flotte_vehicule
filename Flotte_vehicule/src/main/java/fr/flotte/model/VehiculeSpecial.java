package fr.flotte.model;

public class VehiculeSpecial extends Vehicule implements Assignable, Maintenable, Trackable {
    private String typeSpecial;
    private String positionGPS;
    private boolean modeUrgence;

    public VehiculeSpecial(String immatriculation, String marque, String modele, double kilometrage, String typeSpecial, String positionGPS, boolean modeUrgence) {
        super(immatriculation, marque, modele, kilometrage);
        this.typeSpecial = typeSpecial;
        this.positionGPS = positionGPS;
        this.modeUrgence = modeUrgence;
    }

    public String getTypeSpecial() { return typeSpecial; }
    public String getPositionGPS() { return positionGPS; }
    public boolean isModeUrgence() { return modeUrgence; }
    public void setTypeSpecial(String typeSpecial) { this.typeSpecial = typeSpecial; }
    public void setPositionGPS(String positionGPS) { this.positionGPS = positionGPS; }
    public void setModeUrgence(boolean modeUrgence) { this.modeUrgence = modeUrgence; }

    /*@Override
    public void assigner(Mission mission) {
        ajouterMission(mission);
        setEtat(EtatVehicule.UTILISE);

        if (mission.urgence()) {
        activerUrgence();
    }
    }*/

    @Override
    public boolean estDisponible() {
        return getEtat() == EtatVehicule.DISPONIBLE;
    }

    @Override
    public void effectuerMaintenance() {
        setEtat(EtatVehicule.EN_MAINTENANCE);
    }

    /*@Override
    public void signalerIncident(Incident incident) {
        ajouterIncident(incident);
        setEtat(EtatVehicule.EN_MAINTENANCE);
    }*/

    @Override
    public String getPositionActuelle() {
        return getPositionGPS();
    }

    public boolean urgence() {
        return modeUrgence;
    }
    public boolean activerUrgence() {
        setEtat(EtatVehicule.UTILISE);
        return true;
    }

    public void desactiverUrgence() {
        modeUrgence = false;
        setEtat(EtatVehicule.DISPONIBLE);
    }

    public void mettreAJourPosition(String position) {
        setPositionGPS(position);
    }

    @Override
    public String toString() {
        return super.toString() + "VehiculeSpecial{" +
                "typeSpecial='" + typeSpecial + '\'' +
                ", positionGPS='" + positionGPS + '\'' +
                ", modeUrgence=" + modeUrgence +
                '}';
    }
}
