package fr.flotte.model;

import java.io.Serializable;

public abstract class Mission implements Serializable {
    private String itineraire;
    private String statut; // "En cours", "Terminée"

    public Mission(String itineraire) {
        this.itineraire = itineraire;
        this.statut = "En cours";
    }

    // Méthode abstraite : chaque type de mission aura son propre rapport
    public abstract String genererRapport();

    public String getItineraire() { return itineraire; }
    public String getStatut() { return statut; }
}