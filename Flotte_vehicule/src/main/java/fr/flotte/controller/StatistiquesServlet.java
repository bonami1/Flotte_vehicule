package fr.flotte.controller;

import fr.flotte.model.GestionnaireOperationnel;
import fr.flotte.model.GestionnaireMaintenance;
import fr.flotte.model.Mission;
import fr.flotte.util.PersistanceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/statistiques")
public class StatistiquesServlet extends HttpServlet {

    private GestionnaireOperationnel<Mission> gestionnaire;
    private GestionnaireMaintenance gestionnaireMaintenance;

    @Override
    public void init() {
        gestionnaire = GestionnaireOperationnel.getInstance();
        GestionnaireMaintenance charge = PersistanceUtil.chargerMaintenance();
        if (charge != null) {
            GestionnaireMaintenance.setInstance(charge);
        }
        gestionnaireMaintenance = GestionnaireMaintenance.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Missions
        request.setAttribute("nbEnCours",          gestionnaire.nbMissionsEnCours());
        request.setAttribute("nbTerminees",         gestionnaire.nbMissionsTerminees());
        request.setAttribute("nbLongues",           gestionnaire.nbMissionsLongues());
        request.setAttribute("nbCourtes",           gestionnaire.nbMissionsCourtes());
        request.setAttribute("nbChauffeursDispos",  gestionnaire.nbChauffeursDisponibles());
        request.setAttribute("nbChauffeursOccupes", gestionnaire.nbChauffeursOccupes());
        request.setAttribute("totalMissions",       gestionnaire.getMissions().size());
        request.setAttribute("totalChauffeurs",     gestionnaire.getListeChauffeurs().size());
        request.setAttribute("tauxDispo",
                String.format("%.1f", gestionnaire.tauxDisponibiliteChauffeurs()));
        request.setAttribute("statsParStatut",      gestionnaire.statistiquesParStatut());

        // Incidents
        request.setAttribute("totalIncidents",       gestionnaireMaintenance.getIncidents().size());
        request.setAttribute("nbIncidentsTraites",   gestionnaireMaintenance.nbIncidentsTraites());
        request.setAttribute("nbIncidentsNonTraites",gestionnaireMaintenance.nbIncidentsNonTraites());
        request.setAttribute("coutTotal",
                String.format("%.2f", gestionnaireMaintenance.coutTotal()));
        request.setAttribute("coutMoyen",
                String.format("%.2f", gestionnaireMaintenance.coutMoyen()));
        request.setAttribute("statsIncidentsParType", gestionnaireMaintenance.statistiquesParType());

        request.getRequestDispatcher("/statistiques.jsp").forward(request, response);
    }
}