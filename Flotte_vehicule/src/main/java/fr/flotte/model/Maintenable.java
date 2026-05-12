package fr.flotte.model;

import java.util.List;

public interface Maintenable {
    boolean estEnMaintenance();
    void signalerIncident(Incident incident);
    List<Incident> getHistoriqueIncidents();
}