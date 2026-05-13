package fr.flotte.controller;

import fr.flotte.exception.ChauffeurIndisponibleException;
import fr.flotte.exception.MissionDejaTermineeException;
import fr.flotte.model.*;
import fr.flotte.model.EtatVehicule;
import fr.flotte.util.PersistanceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {

    private GestionnaireOperationnel<Mission> gestionnaire;
    private RegistreVehicule<Vehicule> registre;

    @Override
    public void init() {
        gestionnaire = GestionnaireOperationnel.getInstance();
        registre = RegistreVehicule.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id != null) {
            Mission mission = gestionnaire.trouverMission(id).orElse(null);
            if (mission == null) {
                response.sendRedirect(request.getContextPath() + "/missions");
                return;
            }
            List<Chauffeur> chauffeursDisponibles = gestionnaire.getListeChauffeurs().stream()
                    .filter(Chauffeur::estDisponible)
                    .collect(Collectors.toList());
            List<Vehicule> vehiculesDisponibles = registre.listerTous().stream()
                    .filter(Vehicule::estDisponible)
                    .collect(Collectors.toList());
            request.setAttribute("mission", mission);
            request.setAttribute("chauffeursDisponibles", chauffeursDisponibles);
            request.setAttribute("vehiculesDisponibles", vehiculesDisponibles);
            transferFlash(request);
            request.getRequestDispatcher("/detail-mission.jsp").forward(request, response);
            return;
        }

        String filtreStatut   = request.getParameter("filtreStatut");
        String filtreType     = request.getParameter("filtreType");
        String filtreChauffeur = request.getParameter("filtreChauffeur");
        String tri   = request.getParameter("tri");
        String ordre = request.getParameter("ordre");

        List<Mission> listeMissions;
        boolean hasFilter = (filtreStatut != null && !filtreStatut.isEmpty())
                || (filtreType != null && !filtreType.isEmpty())
                || (filtreChauffeur != null && !filtreChauffeur.isEmpty());

        if (hasFilter) {
            listeMissions = gestionnaire.filtrerMulticriteres(filtreStatut, filtreType, filtreChauffeur);
        } else if (tri != null && !tri.isEmpty()) {
            listeMissions = gestionnaire.trierPar(tri, !"desc".equals(ordre));
        } else {
            listeMissions = gestionnaire.getMissions();
        }

        request.setAttribute("listeMissions", listeMissions);
        request.setAttribute("listeChauffeurs", gestionnaire.getListeChauffeurs());
        request.setAttribute("filtreStatut", filtreStatut);
        request.setAttribute("filtreType", filtreType);
        request.setAttribute("filtreChauffeur", filtreChauffeur);
        request.setAttribute("tri", tri);
        request.setAttribute("ordre", ordre);
        transferFlash(request);
        request.getRequestDispatcher("/missions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        try {
            if ("create".equals(action)) {
                creerMission(request);
                setFlash(request, "Mission creee avec succes.", "success");
            } else if ("delete".equals(action)) {
                gestionnaire.supprimerMission(id);
                PersistanceUtil.sauvegarder(gestionnaire);
                setFlash(request, "Mission supprimee.", "warning");
            } else if ("terminer".equals(action)) {
                Mission m = gestionnaire.trouverMission(id).orElse(null);
                if (m != null && m.getVehiculeId() != null) {
                    registre.trouverParImmatriculation(m.getVehiculeId())
                            .ifPresent(v -> v.setEtat(EtatVehicule.DISPONIBLE));
                }
                gestionnaire.terminerMission(id);
                PersistanceUtil.sauvegarder(gestionnaire);
                setFlash(request, "Mission terminee avec succes.", "success");
            } else if ("affecter".equals(action)) {
                gestionnaire.affecterChauffeur(id, request.getParameter("chauffeurId"));
                PersistanceUtil.sauvegarder(gestionnaire);
                setFlash(request, "Chauffeur affecte avec succes.", "success");
            } else if ("affecterVehicule".equals(action)) {
                String immat = request.getParameter("vehiculeImmat");
                Mission m = gestionnaire.trouverMission(id).orElse(null);
                if (m != null && immat != null && !immat.isEmpty()) {
                    if (m.getVehiculeId() != null) {
                        registre.trouverParImmatriculation(m.getVehiculeId())
                                .ifPresent(v -> v.setEtat(EtatVehicule.DISPONIBLE));
                    }
                    m.setVehiculeId(immat);
                    registre.trouverParImmatriculation(immat)
                            .ifPresent(v -> v.setEtat(EtatVehicule.UTILISE));
                    PersistanceUtil.sauvegarder(gestionnaire);
                    setFlash(request, "Vehicule affecte avec succes.", "success");
                }
            }
        } catch (MissionDejaTermineeException | ChauffeurIndisponibleException e) {
            setFlash(request, e.getMessage(), "danger");
        } catch (Exception e) {
            setFlash(request, "Erreur : " + e.getMessage(), "danger");
        }

        if (id != null && ("affecter".equals(action) || "terminer".equals(action))) {
            response.sendRedirect(request.getContextPath() + "/missions?id=" + id);
        } else {
            response.sendRedirect(request.getContextPath() + "/missions");
        }
    }

    private void creerMission(HttpServletRequest request) {
        String itineraire = request.getParameter("itineraire");
        String type = request.getParameter("type");
        Mission mission;
        if ("longue".equals(type)) {
            int pauses = 0;
            try { pauses = Integer.parseInt(request.getParameter("nombrePauses")); } catch (Exception ignored) {}
            mission = new MissionLongue(itineraire, pauses);
        } else {
            mission = new MissionCourte(itineraire);
        }
        gestionnaire.ajouterMission(mission);
        PersistanceUtil.sauvegarder(gestionnaire);
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