package fr.flotte.model;

import fr.flotte.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class RegistreVehicule<T extends Vehicule> {

    // ── Singleton ────────────────────────────────────────────────────────────
    private static RegistreVehicule<Vehicule> instance;

    public static RegistreVehicule<Vehicule> getInstance() {
        if (instance == null) {
            instance = new RegistreVehicule<>();
            // Données de démonstration
            instance.ajouter(new VehiculeLeger("AB-123-CD", "Renault", "Clio", 45000, 5, 6.2));
            instance.ajouter(new VehiculeLourd("EF-456-GH", "Volvo", "FH16", 320000, 25.0, 4));
            instance.ajouter(new VehiculeSpecial("IJ-789-KL", "Mercedes", "Sprinter", 98000, "Ambulance", "48.8566,2.3522", false));
        }
        return instance;
    }

    private RegistreVehicule() {}

    // ── Données ──────────────────────────────────────────────────────────────
    private final List<T> vehicules = new ArrayList<>();

    // ── CRUD ─────────────────────────────────────────────────────────────────

    public void ajouter(T vehicule) {
        boolean existe = vehicules.stream()
                .anyMatch(v -> v.getImmatriculation().equalsIgnoreCase(vehicule.getImmatriculation()));
        if (existe) throw new IllegalArgumentException(
                "Immatriculation déjà enregistrée : " + vehicule.getImmatriculation());
        vehicules.add(vehicule);
    }

    public Optional<T> trouverParImmatriculation(String immatriculation) {
        return vehicules.stream()
                .filter(v -> v.getImmatriculation().equalsIgnoreCase(immatriculation))
                .findFirst();
    }

    public List<T> listerTous() {
        return Collections.unmodifiableList(vehicules);
    }

    public boolean modifier(String immatriculation, String nouvelleMarque,
                            String nouveauModele, double nouveauKilometrage) {
        Optional<T> opt = trouverParImmatriculation(immatriculation);
        opt.ifPresent(v -> {
            v.setMarque(nouvelleMarque);
            v.setModele(nouveauModele);
            v.setKilometrage(nouveauKilometrage);
        });
        return opt.isPresent();
    }

    public boolean supprimer(String immatriculation) {
        return vehicules.removeIf(
                v -> v.getImmatriculation().equalsIgnoreCase(immatriculation));
    }

    // ── FILTRAGE MULTICRITÈRES ────────────────────────────────────────────────

    public List<T> filtrer(String marque, EtatVehicule etat, Double kilometrageMax) {
        return vehicules.stream()
                .filter(v -> marque == null || v.getMarque().equalsIgnoreCase(marque))
                .filter(v -> etat == null || v.getEtat() == etat)
                .filter(v -> kilometrageMax == null || v.getKilometrage() <= kilometrageMax)
                .collect(Collectors.toList());
    }

    public List<T> filtrerParCritere(java.util.function.Predicate<T> critere) {
        return vehicules.stream().filter(critere).collect(Collectors.toList());
    }

    // ── TRI DYNAMIQUE ─────────────────────────────────────────────────────────

    public enum ColonneTri { IMMATRICULATION, MARQUE, MODELE, KILOMETRAGE, ETAT }

    public List<T> trier(ColonneTri colonne, boolean ascendant) {
        Comparator<T> comp = switch (colonne) {
            case IMMATRICULATION -> Comparator.comparing(Vehicule::getImmatriculation);
            case MARQUE          -> Comparator.comparing(Vehicule::getMarque);
            case MODELE          -> Comparator.comparing(Vehicule::getModele);
            case KILOMETRAGE     -> Comparator.comparingDouble(Vehicule::getKilometrage);
            case ETAT            -> Comparator.comparing(v -> v.getEtat().name());
        };
        if (!ascendant) comp = comp.reversed();
        return vehicules.stream().sorted(comp).collect(Collectors.toList());
    }

    // ── STATISTIQUES ──────────────────────────────────────────────────────────

    public long compterParEtat(EtatVehicule etat) {
        return vehicules.stream().filter(v -> v.getEtat() == etat).count();
    }

    public double kilometrageMoyen() {
        return vehicules.stream()
                .mapToDouble(Vehicule::getKilometrage)
                .average().orElse(0);
    }

    public double coutTotalMaintenance() {
        return vehicules.stream()
                .mapToDouble(Vehicule::calculerCoutMaintenance)
                .sum();
    }

    public Map<String, Long> repartitionParMarque() {
        return vehicules.stream()
                .collect(Collectors.groupingBy(Vehicule::getMarque, Collectors.counting()));
    }

    public Optional<T> vehiculeAvecPlusHautKilometrage() {
        return vehicules.stream()
                .max(Comparator.comparingDouble(Vehicule::getKilometrage));
    }

    // Méthode utilitaire pour la page d'accueil
    public int getTotalVehicules() {
        return vehicules.size();
    }
}
