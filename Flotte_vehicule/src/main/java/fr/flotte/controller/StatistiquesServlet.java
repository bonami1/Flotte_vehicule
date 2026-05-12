package fr.flotte.controller;

import fr.flotte.model.GestionnaireOperationnel;
import fr.flotte.model.Mission;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/statistiques")
public class StatistiquesServlet extends HttpServlet {

    private GestionnaireOperationnel<Mission> gestionnaire;

    @Override
    public void init() {
        gestionnaire = GestionnaireOperationnel.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nbEnCours",         gestionnaire.nbMissionsEnCours());
        request.setAttribute("nbTerminees",        gestionnaire.nbMissionsTerminees());
        request.setAttribute("nbLongues",          gestionnaire.nbMissionsLongues());
        request.setAttribute("nbCourtes",          gestionnaire.nbMissionsCourtes());
        request.setAttribute("nbChauffeursDispos", gestionnaire.nbChauffeursDisponibles());
        request.setAttribute("nbChauffeursOccupes",gestionnaire.nbChauffeursOccupes());
        request.setAttribute("totalMissions",      gestionnaire.getMissions().size());
        request.setAttribute("totalChauffeurs",    gestionnaire.getListeChauffeurs().size());
        request.setAttribute("tauxDispo",
                String.format("%.1f", gestionnaire.tauxDisponibiliteChauffeurs()));
        request.setAttribute("statsParStatut",     gestionnaire.statistiquesParStatut());
        request.getRequestDispatcher("/statistiques.jsp").forward(request, response);
    }
}