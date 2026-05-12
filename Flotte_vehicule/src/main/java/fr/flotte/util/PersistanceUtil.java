package fr.flotte.util;

import fr.flotte.model.GestionnaireOperationnel;
import fr.flotte.model.GestionnaireMaintenance;
import fr.flotte.model.Mission;

import java.io.*;

public class PersistanceUtil {
    private static final String FICHIER = System.getProperty("java.io.tmpdir")
            + File.separator + "flotte_vehicule.dat";

    private static final String FICHIER_MAINTENANCE = System.getProperty("java.io.tmpdir")
            + File.separator + "flotte_maintenance.dat";

    public static void sauvegarder(GestionnaireOperationnel<Mission> gestionnaire) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER))) {
            oos.writeObject(gestionnaire);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static GestionnaireOperationnel<Mission> charger() {
        File f = new File(FICHIER);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (GestionnaireOperationnel<Mission>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Erreur chargement : " + e.getMessage());
            return null;
        }
    }

    public static void sauvegarderMaintenance(GestionnaireMaintenance gestionnaire) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER_MAINTENANCE))) {
            oos.writeObject(gestionnaire);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde maintenance : " + e.getMessage());
        }
    }

    public static GestionnaireMaintenance chargerMaintenance() {
        File f = new File(FICHIER_MAINTENANCE);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (GestionnaireMaintenance) ois.readObject();
        } catch (Exception e) {
            System.err.println("Erreur chargement maintenance : " + e.getMessage());
            return null;
        }
    }
}