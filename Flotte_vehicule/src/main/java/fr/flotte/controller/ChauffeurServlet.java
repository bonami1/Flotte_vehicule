package fr.flotte.controller;

import fr.flotte.model.Chauffeur;
import fr.flotte.model.GestionnaireOperationnel;
import fr.flotte.model.Mission;
import fr.flotte.util.PersistanceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chauffeurs")
public class ChauffeurServlet extends HttpServlet {

    private GestionnaireOperationnel<Mission> gestionnaire;

    @Override
    public void init() {
        gestionnaire = GestionnaireOperationnel.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listeChauffeurs", gestionnaire.getListeChauffeurs());
        transferFlash(request);
        request.getRequestDispatcher("/chauffeurs.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String nom    = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String permis = request.getParameter("permis");
                if (nom == null || nom.trim().isEmpty() || prenom == null || prenom.trim().isEmpty()) {
                    throw new IllegalArgumentException("Nom et prenom obligatoires.");
                }
                Chauffeur c = new Chauffeur(nom.trim(), prenom.trim(),
                        permis != null ? permis.trim() : "");
                gestionnaire.ajouterChauffeur(c);
                PersistanceUtil.sauvegarder(gestionnaire);
                setFlash(request, "Chauffeur " + c.getNomComplet() + " ajoute.", "success");

            } else if ("update".equals(action)) {
                Chauffeur c = gestionnaire.trouverChauffeur(request.getParameter("id"));
                if (c != null) {
                    c.setNom(request.getParameter("nom").trim());
                    c.setPrenom(request.getParameter("prenom").trim());
                    c.setPermis(request.getParameter("permis").trim());
                    PersistanceUtil.sauvegarder(gestionnaire);
                    setFlash(request, "Chauffeur mis a jour.", "success");
                }

            } else if ("delete".equals(action)) {
                gestionnaire.supprimerChauffeur(request.getParameter("id"));
                PersistanceUtil.sauvegarder(gestionnaire);
                setFlash(request, "Chauffeur supprime.", "warning");
            }
        } catch (Exception e) {
            setFlash(request, "Erreur : " + e.getMessage(), "danger");
        }

        response.sendRedirect(request.getContextPath() + "/chauffeurs");
    }

    private void setFlash(HttpServletRequest req, String msg, String type) {
        req.getSession().setAttribute("flashMessage", msg);
        req.getSession().setAttribute("flashType", type);
    }

    private void transferFlash(HttpServletRequest req) {
        req.setAttribute("flashMessage", req.getSession().getAttribute("flashMessage"));
        req.setAttribute("flashType",    req.getSession().getAttribute("flashType"));
        req.getSession().removeAttribute("flashMessage");
        req.getSession().removeAttribute("flashType");
    }
}