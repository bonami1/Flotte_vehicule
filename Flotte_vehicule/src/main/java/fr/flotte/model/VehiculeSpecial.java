package fr.flotte.model;

import java.util.List;

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
        if (!estDisponible()) {
            throw new VehiculeIndisponibleException(
                "Le véhicule est indisponible"
            );
        } else {
            ajouterMission(mission);
            setEtat(EtatVehicule.UTILISE);

            if (mission.urgence()) {
                activerUrgence();
            }
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

    @Override
    public String getSuiviItineraire() {
        return "";
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

    @Override
    public boolean estEnMaintenance() {
        return false;
    }

    @Override
    public void signalerIncident(Incident incident) {

    }

    @Override
    public List<Incident> getHistoriqueIncidents() {
        return List.of();
    }
}
