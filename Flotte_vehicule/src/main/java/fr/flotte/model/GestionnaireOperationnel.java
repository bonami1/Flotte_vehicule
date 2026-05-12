package fr.flotte.model;

import fr.flotte.exception.ChauffeurIndisponibleException;
import fr.flotte.exception.MissionDejaTermineeException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestionnaireOperationnel<T extends Mission> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> missions;
    private Map<String, Chauffeur> chauffeurs;
    private transient PriorityQueue<T> filePrioritaire;

    private static GestionnaireOperationnel<Mission> instance;

    public GestionnaireOperationnel() {
        this.missions = new ArrayList<>();
        this.chauffeurs = new LinkedHashMap<>();
        initFilePrioritaire();
    }

    private void initFilePrioritaire() {
        this.filePrioritaire = new PriorityQueue<>((a, b) -> {
            int pa = (a instanceof MissionLongue) ? 0 : 1;
            int pb = (b instanceof MissionLongue) ? 0 : 1;
            return Integer.compare(pa, pb);
        });
        if (missions != null) filePrioritaire.addAll(missions);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initFilePrioritaire();
    }

    public static synchronized GestionnaireOperationnel<Mission> getInstance() {
        if (instance == null) {
            instance = new GestionnaireOperationnel<>();
            initDonneesTest(instance);
        }
        return instance;
    }

    private static void initDonneesTest(GestionnaireOperationnel<Mission> g) {
        Chauffeur alice   = new Chauffeur("Martin",  "Alice",   "Permis B");
        Chauffeur bob     = new Chauffeur("Dupont",  "Bob",     "Permis C");
        Chauffeur charlie = new Chauffeur("Bernard", "Charlie", "Permis B");
        g.ajouterChauffeur(alice);
        g.ajouterChauffeur(bob);
        g.ajouterChauffeur(charlie);

        Mission m1 = new MissionCourte("Paris - Lyon");
        Mission m2 = new MissionLongue("Paris - Marseille", 3);
        Mission m3 = new MissionCourte("Lyon - Grenoble");
        Mission m4 = new MissionLongue("Bordeaux - Nantes", 2);
        g.ajouterMission(m1);
        g.ajouterMission(m2);
        g.ajouterMission(m3);
        g.ajouterMission(m4);

        try { g.affecterChauffeur(m1.getId(), alice.getId()); }   catch (Exception ignored) {}
        try { g.affecterChauffeur(m2.getId(), bob.getId()); }     catch (Exception ignored) {}
        try { g.terminerMission(m3.getId()); }                    catch (Exception ignored) {}
    }

    // ── MISSIONS CRUD ────────────────────────────────────────────────────────

    public void ajouterMission(T mission) {
        missions.add(mission);
        filePrioritaire.add(mission);
    }

    public void supprimerMission(String id) {
        missions.removeIf(m -> m.getId().equals(id));
        filePrioritaire.clear();
        filePrioritaire.addAll(missions);
    }

    public Optional<T> trouverMission(String id) {
        return missions.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void terminerMission(String id) throws MissionDejaTermineeException {
        T mission = trouverMission(id)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable : " + id));
        if ("Terminee".equals(mission.getStatut()) || "Terminée".equals(mission.getStatut())) {
            throw new MissionDejaTermineeException(id);
        }
        mission.setStatut("Terminee");
        if (mission.getChauffeurAssigne() != null) {
            mission.getChauffeurAssigne().setDisponible(true);
        }
    }

    public void affecterChauffeur(String missionId, String chauffeurId)
            throws ChauffeurIndisponibleException, MissionDejaTermineeException {
        T mission = trouverMission(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable"));
        if ("Terminee".equals(mission.getStatut()) || "Terminée".equals(mission.getStatut())) {
            throw new MissionDejaTermineeException(missionId);
        }
        Chauffeur chauffeur = chauffeurs.get(chauffeurId);
        if (chauffeur == null) throw new IllegalArgumentException("Chauffeur introuvable");
        if (!chauffeur.estDisponible()) {
            throw new ChauffeurIndisponibleException(chauffeur.getNomComplet());
        }
        if (mission.getChauffeurAssigne() != null) {
            mission.getChauffeurAssigne().setDisponible(true);
        }
        mission.setChauffeurAssigne(chauffeur);
        chauffeur.setDisponible(false);
        chauffeur.ajouterMissionHistorique(mission);
    }

    // ── CHAUFFEURS CRUD ──────────────────────────────────────────────────────

    public void ajouterChauffeur(Chauffeur c) { chauffeurs.put(c.getId(), c); }
    public void supprimerChauffeur(String id) { chauffeurs.remove(id); }
    public Chauffeur trouverChauffeur(String id) { return chauffeurs.get(id); }

    // ── GETTERS ──────────────────────────────────────────────────────────────

    public List<T> getMissions() { return Collections.unmodifiableList(missions); }
    public List<Chauffeur> getListeChauffeurs() { return new ArrayList<>(chauffeurs.values()); }
    public PriorityQueue<T> getFilePrioritaire() { return new PriorityQueue<>(filePrioritaire); }

    // ── FILTRAGE MULTICRITERES ───────────────────────────────────────────────

    public List<T> filtrer(Predicate<T> critere) {
        return missions.stream().filter(critere).collect(Collectors.toList());
    }

    public List<T> filtrerMulticriteres(String statut, String type, String chauffeurId) {
        Predicate<T> pred = m -> true;
        if (statut != null && !statut.isEmpty())
            pred = pred.and(m -> statut.equals(m.getStatut()));
        if (type != null && !type.isEmpty())
            pred = pred.and(m -> type.equals(m.getType()));
        if (chauffeurId != null && !chauffeurId.isEmpty())
            pred = pred.and(m -> m.getChauffeurAssigne() != null
                    && chauffeurId.equals(m.getChauffeurAssigne().getId()));
        return filtrer(pred);
    }

    // ── TRI DYNAMIQUE ────────────────────────────────────────────────────────

    public List<T> trierPar(String colonne, boolean croissant) {
        Comparator<T> comp;
        if ("statut".equals(colonne)) {
            comp = (a, b) -> a.getStatut().compareTo(b.getStatut());
        } else if ("type".equals(colonne)) {
            comp = (a, b) -> a.getType().compareTo(b.getType());
        } else if ("itineraire".equals(colonne)) {
            comp = (a, b) -> a.getItineraire().compareTo(b.getItineraire());
        } else if ("date".equals(colonne)) {
            comp = (a, b) -> a.getDateDebut().compareTo(b.getDateDebut());
        } else {
            comp = (a, b) -> a.getId().compareTo(b.getId());
        }
        return missions.stream()
                .sorted(croissant ? comp : comp.reversed())
                .collect(Collectors.toList());
    }

    // ── STATISTIQUES ─────────────────────────────────────────────────────────

    public long nbMissionsEnCours() {
        return missions.stream().filter(m -> "En cours".equals(m.getStatut())).count();
    }

    public long nbMissionsTerminees() {
        return missions.stream()
                .filter(m -> "Terminee".equals(m.getStatut()) || "Terminée".equals(m.getStatut()))
                .count();
    }

    public long nbMissionsLongues() {
        return missions.stream().filter(m -> m instanceof MissionLongue).count();
    }

    public long nbMissionsCourtes() {
        return missions.stream().filter(m -> m instanceof MissionCourte).count();
    }

    public long nbChauffeursDisponibles() {
        return chauffeurs.values().stream().filter(Chauffeur::estDisponible).count();
    }

    public long nbChauffeursOccupes() {
        return chauffeurs.values().stream().filter(c -> !c.estDisponible()).count();
    }

    public double tauxDisponibiliteChauffeurs() {
        if (chauffeurs.isEmpty()) return 0;
        return (double) nbChauffeursDisponibles() / chauffeurs.size() * 100;
    }

    public Map<String, Long> statistiquesParStatut() {
        return missions.stream()
                .collect(Collectors.groupingBy(Mission::getStatut, Collectors.counting()));
    }
}