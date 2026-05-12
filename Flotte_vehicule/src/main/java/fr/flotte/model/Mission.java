package fr.flotte.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Mission implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String itineraire;
    private String statut;
    private Chauffeur chauffeurAssigne;
    private LocalDate dateDebut;

    public Mission(String itineraire) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.itineraire = itineraire;
        this.statut = "En cours";
        this.dateDebut = LocalDate.now();
    }

    public abstract String genererRapport();
    public abstract String getType();

    public String getId() { return id; }
    public String getItineraire() { return itineraire; }
    public void setItineraire(String itineraire) { this.itineraire = itineraire; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Chauffeur getChauffeurAssigne() { return chauffeurAssigne; }
    public void setChauffeurAssigne(Chauffeur chauffeur) { this.chauffeurAssigne = chauffeur; }
    public LocalDate getDateDebut() { return dateDebut; }
}