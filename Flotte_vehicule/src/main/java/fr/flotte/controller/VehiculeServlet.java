package fr.flotte.controller;


import fr.flotte.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vehicules")
public class VehiculeServlet extends HttpServlet {

    private RegistreVehicule<Vehicule> registre;

    @Override
    public void init() {
        registre = RegistreVehicule.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            action = "liste";
        }

        switch (action) {

            case "liste":
                handleListe(req, resp);
                break;

            case "detail":
                handleDetail(req, resp);
                break;

            case "formulaire":
                handleFormulaire(req, resp);
                break;

            case "supprimer":
                handleSupprimer(req, resp);
                break;

            case "statistiques":
                handleStatistiques(req, resp);
                break;

            default:
                handleListe(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("creer".equals(action)) {

            handleCreer(req, resp);

        } else if ("modifier".equals(action)) {

            handleModifier(req, resp);

        } else {

            resp.sendRedirect(
                    req.getContextPath() + "/vehicules?action=liste"
            );
        }
    }

    // =====================================================
    // LISTE
    // =====================================================

    private void handleListe(HttpServletRequest req,
                             HttpServletResponse resp)
            throws ServletException, IOException {

        List<Vehicule> resultats = registre.getTous();

        req.setAttribute("vehicules", resultats);
        req.setAttribute("etats", EtatVehicule.values());

        req.getRequestDispatcher("/vehicules_liste.jsp")
                .forward(req, resp);
    }

    // =====================================================
    // DETAIL
    // =====================================================

    private void handleDetail(HttpServletRequest req,
                              HttpServletResponse resp)
            throws ServletException, IOException {

        String immat = req.getParameter("immat");

        var opt = registre.trouverParImmatriculation(immat);

        if (opt.isPresent()) {

            req.setAttribute("vehicule", opt.get());

            req.getRequestDispatcher("/vehicules_detail.jsp")
                    .forward(req, resp);

        } else {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/vehicules?action=liste&erreur=introuvable"
            );
        }
    }

    // =====================================================
    // FORMULAIRE
    // =====================================================

    private void handleFormulaire(HttpServletRequest req,
                                  HttpServletResponse resp)
            throws ServletException, IOException {

        String immat = req.getParameter("immat");

        if (immat != null) {

            registre.trouverParImmatriculation(immat)
                    .ifPresent(v -> req.setAttribute("vehicule", v));
        }

        req.setAttribute("etats", EtatVehicule.values());

        req.getRequestDispatcher("/vehicules_formulaire.jsp")
                .forward(req, resp);
    }

    // =====================================================
    // CREER
    // =====================================================

    private void handleCreer(HttpServletRequest req,
                             HttpServletResponse resp)
            throws IOException, ServletException {

        try {

            String type   = req.getParameter("type");
            String immat  = req.getParameter("immatriculation");
            String marque = req.getParameter("marque");
            String modele = req.getParameter("modele");

            double km = parseDouble(
                    req.getParameter("kilometrage")
            );

            Vehicule v;

            switch (type) {

                case "LEGER":

                    int nombrePlaces = parseInt(
                            req.getParameter("nombrePlaces")
                    );

                    double consommation = parseDouble(
                            req.getParameter("consommation")
                    );

                    v = new VehiculeLeger(
                            immat,
                            marque,
                            modele,
                            km,
                            nombrePlaces,
                            consommation
                    );

                    break;

                case "LOURD":

                    double capaciteCharge = parseDouble(
                            req.getParameter("capaciteCharge")
                    );

                    int nombreEssieux = parseInt(
                            req.getParameter("nombreEssieux")
                    );

                    v = new VehiculeLourd(
                            immat,
                            marque,
                            modele,
                            km,
                            capaciteCharge,
                            nombreEssieux
                    );

                    break;

                case "SPECIAL":

                    String typeSpecial =
                            req.getParameter("typeSpecial");

                    String positionGPS =
                            req.getParameter("positionGPS");

                    boolean modeUrgence =
                            req.getParameter("modeUrgence") != null;

                    v = new VehiculeSpecial(
                            immat,
                            marque,
                            modele,
                            km,
                            typeSpecial,
                            positionGPS,
                            modeUrgence
                    );

                    break;

                default:
                    throw new IllegalArgumentException(
                            "Type de véhicule invalide"
                    );
            }

            registre.ajouter(v);

            resp.sendRedirect(
                    req.getContextPath()
                            + "/vehicules?action=liste&succes=cree"
            );

        } catch (Exception e) {

            e.printStackTrace();

            req.setAttribute("erreur", e.getMessage());

            req.getRequestDispatcher("/vehicules_formulaire.jsp")
                    .forward(req, resp);
        }
    }

    // =====================================================
    // MODIFIER
    // =====================================================

    private void handleModifier(HttpServletRequest req,
                                HttpServletResponse resp)
            throws IOException {

        try {

            String immat  = req.getParameter("immatriculation");
            String marque = req.getParameter("marque");
            String modele = req.getParameter("modele");

            double km = parseDouble(
                    req.getParameter("kilometrage")
            );

            registre.modifier(
                    immat,
                    marque,
                    modele,
                    km
            );

            resp.sendRedirect(
                    req.getContextPath()
                            + "/vehicules?action=detail&immat="
                            + immat
            );

        } catch (Exception e) {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/vehicules?action=liste&erreur="
                            + e.getMessage()
            );
        }
    }

    // =====================================================
    // SUPPRIMER
    // =====================================================

    private void handleSupprimer(HttpServletRequest req,
                                 HttpServletResponse resp)
            throws IOException {

        registre.supprimer(
                req.getParameter("immat")
        );

        resp.sendRedirect(
                req.getContextPath()
                        + "/vehicules?action=liste&succes=supprime"
        );
    }

    // =====================================================
    // STATISTIQUES
    // =====================================================

    private void handleStatistiques(HttpServletRequest req,
                                    HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute(
                "nbDisponibles",
                registre.compterParEtat(EtatVehicule.DISPONIBLE)
        );

        req.setAttribute(
                "nbEnMission",
                registre.compterParEtat(EtatVehicule.UTILISE)
        );

        req.setAttribute(
                "nbMaintenance",
                registre.compterParEtat(EtatVehicule.EN_MAINTENANCE)
        );

        req.setAttribute(
                "kmMoyen",
                String.format("%.0f",
                        registre.kilometrageMoyen())
        );

        req.setAttribute(
                "repartitionMarques",
                registre.repartitionParMarque()
        );

        registre.vehiculeAvecPlusHautKilometrage()
                .ifPresent(v ->
                        req.setAttribute("vehiculeKmMax", v)
                );

        req.getRequestDispatcher("/vehicules_statistiques.jsp")
                .forward(req, resp);
    }

    // =====================================================
    // UTILITAIRES
    // =====================================================

    private int parseInt(String value) {

        if (value == null || value.isBlank()) {
            return 0;
        }

        return Integer.parseInt(value);
    }

    private double parseDouble(String value) {

        if (value == null || value.isBlank()) {
            return 0;
        }

        return Double.parseDouble(value);
    }
}
