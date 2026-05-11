package fr.flotte.model;

import java.util.Scanner;

public class VehiculeLeger extends Vehicule implements Assignable, Maintenable {
    Scanner sc = new Scanner(System.in);

    private int nombrePlaces;
    private double consommationMoyenne;

    public VehiculeLeger(String immatriculation, String marque, String modele, double kilometrage, int nombrePlaces, double consommationMoyenne) {
        super(immatriculation, marque, modele, kilometrage);
        this.nombrePlaces = nombrePlaces;
        this.consommationMoyenne = consommationMoyenne;
    }

    public int getNombrePlaces() {return nombrePlaces;}
    public double getConsommationMoyenne() {return consommationMoyenne;}
    public void setNombrePlaces(int nombrePlaces) {this.nombrePlaces = nombrePlaces;}
    public void setConsommationMoyenne(double consommationMoyenne) {this.consommationMoyenne = consommationMoyenne;}

    /*@Override
    public void assigner(Mission mission) {
        ajouterMission(mission);
        setEtat(EtatVehicule.UTILISE);
    }*/

    @Override
    public boolean estDisponible() {
        return getEtat() == EtatVehicule.DISPONIBLE;
    }

    @Override
    public void effectuerMaintenance() {
        setEtat(EtatVehicule.EN_MAINTENANCE);
    };

    /*@Override
    public void signalerIncident(Incident incident) {
        ajouterIncident(incident);
        setEtat(EtatVehicule.EN_MAINTENANCE);
    }*/

    public double calculerConsommation(double distance) {
        return (consommationMoyenne * distance) / 100;
    }

    public boolean peutTransporter(int nbPersonnes) {
        return nombrePlaces >= nbPersonnes;
    }
}
