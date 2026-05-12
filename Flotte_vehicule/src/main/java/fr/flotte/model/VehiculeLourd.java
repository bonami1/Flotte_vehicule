package fr.flotte.model;

import fr.flotte.exception.CapaciteDepasseeException;

import java.util.List;

public class VehiculeLourd extends Vehicule implements Assignable, Maintenable {
    private double capaciteCharge;
    private int nombreEssieux;

    public VehiculeLourd(String immatriculation, String marque, String modele, double kilometrage, double capaciteCharge, int nombreEssieux) {
        super(immatriculation, marque, modele, kilometrage);
        this.capaciteCharge = capaciteCharge;
        this.nombreEssieux = nombreEssieux;
    }

    public double getCapaciteCharge() { return capaciteCharge; }
    public int getNombreEssieux() { return nombreEssieux; }
    public void setCapaciteCharge(double capaciteCharge) { this.capaciteCharge = capaciteCharge; }
    public void setNombreEssieux(int nombreEssieux) {this.nombreEssieux = nombreEssieux;}

    /*@Override
    public void assigner(Mission mission) {
    if (!estDisponible()) {
        throw new VehiculeIndisponibleException(
            "Le véhicule est indisponible"
        );
    } else {
        ajouterMission(mission);
        setEtat(EtatVehicule.UTILISE);
        }
    }*/

    @Override
    public boolean estDisponible() {
        return getEtat() == EtatVehicule.DISPONIBLE;
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

    @Override
    public void effectuerMaintenance() {
        setEtat(EtatVehicule.EN_MAINTENANCE);
    }

    /*@Override
    public void signalerIncident(Incident incident) {
        ajouterIncident(incident);
        setEtat(EtatVehicule.EN_MAINTENANCE);
    }*/

    public boolean verifierCharge(double poids) throws CapaciteDepasseeException {
        if (poids > capaciteCharge) {
            throw new CapaciteDepasseeException(
                    "Poids trop élevé pour ce véhicule"
            );
        } else {
            return poids <= capaciteCharge;
        }
    }

    public double calculerUsure() {
        double usureKilometrage = getKilometrage() / 10000;
        //double usureIncidents = historiqueIncidents.size() * 5;
        double usureCharge = capaciteCharge / 1000;

        return usureKilometrage + /*usureIncidents*/ + usureCharge;
    }

    @Override
    public String toString() {
        return "VehiculeLourd{" +
                "capaciteCharge=" + capaciteCharge +
                ", nombreEssieux=" + nombreEssieux +
                '}';
    }
}
