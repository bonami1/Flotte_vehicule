package fr.flotte.model;

import fr.flotte.exception.IncidentDejaTraiteException;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestionnaireMaintenance implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Incident> incidents;
    private static GestionnaireMaintenance instance;

    public GestionnaireMaintenance() {
        this.incidents = new ArrayList<>();
    }

    public static synchronized GestionnaireMaintenance getInstance() {
        if (instance == null) {
            instance = new GestionnaireMaintenance();
            initDonneesTest(instance);
        }
        return instance;
    }

    public static synchronized void setInstance(GestionnaireMaintenance g) {
        instance = g;
    }

    private static void initDonneesTest(GestionnaireMaintenance g) {
        Incident i1 = new PanneMecanique("Pneu avant gauche creve", 120.0, "VEH-001", "Pneu");
        Incident i2 = new Accident("Accrochage en stationnement", 850.0, "VEH-002", "Leger");
        Incident i3 = new PanneMecanique("Defaillance systeme de freinage", 600.0, "VEH-001", "Freins");
        g.ajouterIncident(i1);
        g.ajouterIncident(i2);
        g.ajouterIncident(i3);
        try { g.marquerTraite(i1.getId()); } catch (Exception ignored) {}
    }

    // ── CRUD ─────────────────────────────────────────────────────────

    public void ajouterIncident(Incident incident) {
        incidents.add(incident);
    }

    public void supprimerIncident(String id) {
        incidents.removeIf(i -> i.getId().equals(id));
    }

    public Optional<Incident> trouverIncident(String id) {
        return incidents.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public void marquerTraite(String id) throws IncidentDejaTraiteException {
        Incident incident = trouverIncident(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident introuvable : " + id));
        if (incident.isTraite()) {
            throw new IncidentDejaTraiteException(id);
        }
        incident.setTraite(true);
    }

    // ── GETTER ───────────────────────────────────────────────────────

    public List<Incident> getIncidents() { return Collections.unmodifiableList(incidents); }

    // ── FILTRAGE MULTICRITERES ───────────────────────────────────────

    public List<Incident> filtrer(Predicate<Incident> critere) {
        return incidents.stream().filter(critere).collect(Collectors.toList());
    }

    public List<Incident> filtrerMulticriteres(String type, String vehiculeId, String traite) {
        Predicate<Incident> pred = i -> true;
        if (type != null && !type.isEmpty())
            pred = pred.and(i -> type.equals(i.getType()));
        if (vehiculeId != null && !vehiculeId.isEmpty())
            pred = pred.and(i -> vehiculeId.equalsIgnoreCase(i.getVehiculeId()));
        if (traite != null && !traite.isEmpty()) {
            boolean b = "oui".equals(traite);
            pred = pred.and(i -> i.isTraite() == b);
        }
        return filtrer(pred);
    }

    // ── TRI DYNAMIQUE ────────────────────────────────────────────────

    public List<Incident> trierPar(String colonne, boolean croissant) {
        Comparator<Incident> comp;
        if ("type".equals(colonne)) {
            comp = Comparator.comparing(Incident::getType);
        } else if ("cout".equals(colonne)) {
            comp = Comparator.comparingDouble(Incident::getCoutEstime);
        } else if ("traite".equals(colonne)) {
            comp = Comparator.comparing(Incident::isTraite);
        } else {
            comp = Comparator.comparing(Incident::getDate);
        }
        return incidents.stream()
                .sorted(croissant ? comp : comp.reversed())
                .collect(Collectors.toList());
    }

    // ── STATISTIQUES ─────────────────────────────────────────────────

    public long nbIncidentsNonTraites() {
        return incidents.stream().filter(i -> !i.isTraite()).count();
    }

    public long nbIncidentsTraites() {
        return incidents.stream().filter(Incident::isTraite).count();
    }

    public double coutTotal() {
        return incidents.stream().mapToDouble(Incident::getCoutEstime).sum();
    }

    public double coutMoyen() {
        return incidents.stream().mapToDouble(Incident::getCoutEstime).average().orElse(0);
    }

    public Map<String, Long> statistiquesParType() {
        return incidents.stream()
                .collect(Collectors.groupingBy(Incident::getType, Collectors.counting()));
    }
}