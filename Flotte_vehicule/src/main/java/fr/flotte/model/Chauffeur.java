package fr.flotte.model;

import java.io.Serializable;

public class Chauffeur implements Assignable, Serializable {
    private String nom;
    private String permis;
    private boolean disponible;

    public Chauffeur(String nom, String permis) {
        this.nom = nom;
        this.permis = permis;
        this.disponible = true; // Par défaut, un nouveau chauffeur est libre
    }

    // On implémente la méthode de l'interface
    @Override
    public boolean estDisponible() {
        return disponible;
    }

    // Getters pour afficher les infos dans la JSP
    public String getNom() { return nom; }
    public String getPermis() { return permis; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}