package fr.flotte.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chauffeur implements Assignable, Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nom;
    private String prenom;
    private String permis;
    private boolean disponible;
    private List<Mission> historiqueMissions;

    public Chauffeur(String nom, String prenom, String permis) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.nom = nom;
        this.prenom = prenom;
        this.permis = permis;
        this.disponible = true;
        this.historiqueMissions = new ArrayList<>();
    }

    @Override
    public boolean estDisponible() { return disponible; }

    public void ajouterMissionHistorique(Mission m) {
        historiqueMissions.add(m);
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getPermis() { return permis; }
    public void setPermis(String permis) { this.permis = permis; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public List<Mission> getHistoriqueMissions() { return historiqueMissions; }
    public String getNomComplet() { return prenom + " " + nom; }
}