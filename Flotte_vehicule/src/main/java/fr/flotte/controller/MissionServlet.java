package fr.flotte.controller;

import fr.flotte.model.Chauffeur;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/missions") // L'adresse URL pour voir ta page sera : localhost:8080/missions
public class MissionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. On crée des données de test (simulation)
        List<Chauffeur> maListe = new ArrayList<>();
        maListe.add(new Chauffeur("Alice", "Permis B"));
        maListe.add(new Chauffeur("Bob", "Permis C"));
        maListe.add(new Chauffeur("Charlie", "Permis B"));

        // 2. LE BARÈME : Utilisation d'un Stream pour filtrer les chauffeurs disponibles
        List<Chauffeur> dispos = maListe.stream()
                .filter(Chauffeur::estDisponible)
                .collect(Collectors.toList());

        // 3. On envoie la liste à la vue (JSP)
        request.setAttribute("chauffeursDispo", dispos);

        // 4. On dit au navigateur d'afficher la page missions.jsp
        request.getRequestDispatcher("/missions.jsp").forward(request, response);
    }
}