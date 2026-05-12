package fr.flotte.util;

import fr.flotte.model.GestionnaireOperationnel;
import fr.flotte.model.Mission;

import java.io.*;

public class PersistanceUtil {
    private static final String FICHIER = System.getProperty("java.io.tmpdir")
            + File.separator + "flotte_vehicule.dat";

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
}