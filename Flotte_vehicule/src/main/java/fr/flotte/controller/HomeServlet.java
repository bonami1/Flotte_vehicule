package fr.flotte.controller;

import fr.flotte.model.GestionnaireOperationnel;
import fr.flotte.model.Mission;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private GestionnaireOperationnel<Mission> gestionnaire;

    @Override
    public void init() {
        gestionnaire = GestionnaireOperationnel.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("nbMissionsEnCours",     gestionnaire.nbMissionsEnCours());
        request.setAttribute("nbChauffeursDispos",    gestionnaire.nbChauffeursDisponibles());
        request.setAttribute("totalMissions",         gestionnaire.getMissions().size());
        request.setAttribute("totalChauffeurs",       gestionnaire.getListeChauffeurs().size());
        request.getRequestDispatcher("/accueil.jsp").forward(request, response);
    }
}