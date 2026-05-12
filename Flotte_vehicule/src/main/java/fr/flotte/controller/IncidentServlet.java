package fr.flotte.controller;

import fr.flotte.exception.IncidentDejaTraiteException;
import fr.flotte.model.*;
import fr.flotte.util.PersistanceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/incidents")
public class IncidentServlet extends HttpServlet {

    private GestionnaireMaintenance gestionnaire;

    @Override
    public void init() {
        GestionnaireMaintenance charge = PersistanceUtil.chargerMaintenance();
        if (charge != null) {
            GestionnaireMaintenance.setInstance(charge);
        }
        gestionnaire = GestionnaireMaintenance.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filtreType      = request.getParameter("filtreType");
        String filtreVehicule  = request.getParameter("filtreVehicule");
        String filtreTraite    = request.getParameter("filtreTraite");
        String tri             = request.getParameter("tri");
        String ordre           = request.getParameter("ordre");

        List<Incident> listeIncidents;

        boolean hasFilter = (filtreType != null && !filtreType.isEmpty())
                || (filtreVehicule != null && !filtreVehicule.isEmpty())
                || (filtreTraite != null && !filtreTraite.isEmpty());

        if (hasFilter) {
            listeIncidents = gestionnaire.filtrerMulticriteres(filtreType, filtreVehicule, filtreTraite);
        } else if (tri != null && !tri.isEmpty()) {
            listeIncidents = gestionnaire.trierPar(tri, !"desc".equals(ordre));
        } else {
            listeIncidents = gestionnaire.getIncidents();
        }

        request.setAttribute("listeIncidents",  listeIncidents);
        request.setAttribute("filtreType",      filtreType);
        request.setAttribute("filtreVehicule",  filtreVehicule);
        request.setAttribute("filtreTraite",    filtreTraite);
        request.setAttribute("tri",             tri);
        request.setAttribute("ordre",           ordre);
        transferFlash(request);
        request.getRequestDispatcher("/incidents.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String id     = request.getParameter("id");

        try {
            if ("create".equals(action)) {
                creerIncident(request);
                setFlash(request, "Incident declare avec succes.", "success");
            } else if ("delete".equals(action)) {
                gestionnaire.supprimerIncident(id);
                PersistanceUtil.sauvegarderMaintenance(gestionnaire);
                setFlash(request, "Incident supprime.", "warning");
            } else if ("traiter".equals(action)) {
                gestionnaire.marquerTraite(id);
                PersistanceUtil.sauvegarderMaintenance(gestionnaire);
                setFlash(request, "Incident marque comme traite.", "success");
            }
        } catch (IncidentDejaTraiteException e) {
            setFlash(request, e.getMessage(), "danger");
        } catch (Exception e) {
            setFlash(request, "Erreur : " + e.getMessage(), "danger");
        }

        response.sendRedirect(request.getContextPath() + "/incidents");
    }

    private void creerIncident(HttpServletRequest request) {
        String type        = request.getParameter("type");
        String description = request.getParameter("description");
        String vehiculeId  = request.getParameter("vehiculeId");
        double cout = 0;
        try { cout = Double.parseDouble(request.getParameter("coutEstime")); } catch (Exception ignored) {}

        Incident incident;
        if ("Accident".equals(type)) {
            String gravite = request.getParameter("gravite");
            incident = new Accident(description, cout, vehiculeId, gravite);
        } else {
            String typePanne = request.getParameter("typePanne");
            incident = new PanneMecanique(description, cout, vehiculeId, typePanne);
        }
        gestionnaire.ajouterIncident(incident);
        PersistanceUtil.sauvegarderMaintenance(gestionnaire);
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