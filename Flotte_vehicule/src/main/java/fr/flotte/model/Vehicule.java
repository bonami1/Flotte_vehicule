package fr.flotte.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicule {
    protected String immatriculation;
    protected String marque;
    protected String modele;
    protected double kilometrage;
    protected EtatVehicule etat;
    protected LocalDate dateMiseEnService;

    /*protected List<Incident> historiqueIncidents;
    protected List<Mission> historiqueMissions;*/

    public Vehicule(String immatriculation, String marque, String modele, double kilometrage) {
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.kilometrage = kilometrage;

        this.etat = EtatVehicule.DISPONIBLE;
        this.dateMiseEnService = LocalDate.now();
        /*this.historiqueIncidents = newArrayList<>();
        this.historiqueMissions = newArrayList<>();*/
    }

    public String getImmatriculation() { return immatriculation; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public double getKilometrage() { return kilometrage; }
    public EtatVehicule getEtat() { return etat; }
    public LocalDate getDateMiseEnService() { return dateMiseEnService; }
    /*public List<Incident> getHistoriqueIncidents() {return historiqueIncidents;}
    public List<Mission> getHistoriqueMissions() {return historiqueMissions;}*/

    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setModele(String modele) { this.modele = modele; }
    public void setKilometrage(double kilometrage) { this.kilometrage = kilometrage; }
    public void setEtat(EtatVehicule etat) { this.etat = etat; }


    /*public double calculerCoutMaintenance() {
        return historiqueIncidents.stream()
                .mapToDouble(Incident::getCout)
                .sum();
        // Parcourir la liste d'incidents d'un vehicule et les additionner
    };*/

    /*public void ajouterIncident(Incident incident) {
        historiqueIncidents.add(incident);
    }
    public void ajouterMission(Mission mission) {
        historiqueMissions.add(mission);
    }
    public int getNombreMissions() {
        return historiqueMissions.size();
    }*/

    public abstract boolean estDisponible();

    public abstract void effectuerMaintenance();

    @Override
    public String toString() {return "Vehicule [ " +
                                    "immatriculation : " + getImmatriculation() + " | " +
                                    "marque : " + getMarque() + " | " +
                                    "modele : " + getModele() + " | " +
                                    "Kilometrage : " + getKilometrage() + " | " +
                                    "Etat : " + getEtat() + " ]";
                                    };
}


