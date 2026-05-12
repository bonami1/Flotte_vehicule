package fr.flotte.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Incident implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private LocalDate date;
    private double coutEstime;
    private String description;
    private String vehiculeId;
    private boolean traite;

    public Incident(String description, double coutEstime, String vehiculeId) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.date = LocalDate.now();
        this.coutEstime = coutEstime;
        this.description = description;
        this.vehiculeId = vehiculeId;
        this.traite = false;
    }

    public abstract String genererRapport();
    public abstract String getType();

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public double getCoutEstime() { return coutEstime; }
    public void setCoutEstime(double coutEstime) { this.coutEstime = coutEstime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVehiculeId() { return vehiculeId; }
    public void setVehiculeId(String vehiculeId) { this.vehiculeId = vehiculeId; }
    public boolean isTraite() { return traite; }
    public void setTraite(boolean traite) { this.traite = traite; }
}