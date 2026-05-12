<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FlotteManager — Accueil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        /* ── Bannière vidéo ── */
        .banner {
            position: relative;
            height: 100vh;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .banner video {
            position: absolute;
            top: 50%; left: 50%;
            transform: translate(-50%, -50%);
            min-width: 100%; min-height: 100%;
            object-fit: cover;
            z-index: 0;
        }

        /* Fallback si pas de vidéo */
        .banner-fallback {
            position: absolute;
            inset: 0;
            background: linear-gradient(135deg, #1a1a2e 0%, #16213e 40%, #0f3460 70%, #533483 100%);
            z-index: 0;
        }

        .banner-overlay {
            position: absolute;
            inset: 0;
            background: rgba(0, 0, 0, 0.55);
            z-index: 1;
        }

        .banner-content {
            position: relative;
            z-index: 2;
            text-align: center;
            color: white;
            padding: 2rem;
        }

        .banner-title {
            font-size: 4rem;
            font-weight: 800;
            letter-spacing: 3px;
            text-shadow: 2px 2px 12px rgba(0,0,0,0.7);
        }

        .banner-subtitle {
            font-size: 1.4rem;
            font-weight: 300;
            margin-top: 0.5rem;
            opacity: 0.9;
            letter-spacing: 1px;
        }

        .scroll-hint {
            position: absolute;
            bottom: 2rem;
            z-index: 2;
            color: white;
            opacity: 0.7;
            animation: bounce 2s infinite;
        }

        @keyframes bounce {
            0%, 100% { transform: translateY(0); }
            50%       { transform: translateY(8px); }
        }

        /* ── Cartes ── */
        .cards-section {
            padding: 4rem 0;
            background: #f8f9fa;
        }

        .feature-card {
            position: relative;
            height: 280px;
            border-radius: 12px;
            overflow: hidden;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-decoration: none;
            display: block;
        }

        .feature-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.25);
        }

        .feature-card .bg-img {
            position: absolute;
            inset: 0;
            background-size: cover;
            background-position: center;
            transition: transform 0.4s ease;
        }

        .feature-card:hover .bg-img {
            transform: scale(1.08);
        }

        .feature-card .card-dim {
            position: absolute;
            inset: 0;
            background: linear-gradient(to top, rgba(0,0,0,0.8) 0%, rgba(0,0,0,0.2) 60%, transparent 100%);
            transition: background 0.3s;
        }

        .feature-card:hover .card-dim {
            background: linear-gradient(to top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.3) 60%, transparent 100%);
        }

        .feature-card .card-body-custom {
            position: absolute;
            bottom: 0;
            left: 0; right: 0;
            padding: 1.5rem;
            color: white;
        }

        .feature-card .card-icon {
            font-size: 2.2rem;
            margin-bottom: 0.4rem;
        }

        .feature-card .card-title-custom {
            font-size: 1.5rem;
            font-weight: 700;
            margin-bottom: 0.3rem;
        }

        .feature-card .card-desc {
            font-size: 0.88rem;
            opacity: 0.85;
            margin-bottom: 0.8rem;
        }

        .badge-stat {
            background: rgba(255,255,255,0.2);
            backdrop-filter: blur(4px);
            border: 1px solid rgba(255,255,255,0.3);
            border-radius: 20px;
            padding: 3px 10px;
            font-size: 0.78rem;
            color: white;
            margin-right: 6px;
        }

        .badge-stat.green { background: rgba(25, 135, 84, 0.7); }
        .badge-stat.yellow { background: rgba(255, 193, 7, 0.7); color: #1a1a1a; }

        .coming-soon {
            position: absolute;
            top: 1rem; right: 1rem;
            background: rgba(0,0,0,0.5);
            color: white;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 0.75rem;
            z-index: 2;
        }

        /* ── Footer ── */
        footer {
            background: #1a1a2e;
            color: #aaa;
            text-align: center;
            padding: 1.5rem;
            font-size: 0.85rem;
        }
    </style>
</head>
<body>

<%-- ── Bannière vidéo ─────────────────────────────────────────── --%>
<div class="banner">
    <div class="banner-fallback"></div>
    <%-- Dépose ton fichier vidéo dans src/main/webapp/assets/banner.mp4 --%>
    <video autoplay muted loop playsinline>
        <source src="${pageContext.request.contextPath}/assets/banner.mp4" type="video/mp4">
    </video>
    <div class="banner-overlay"></div>
    <div class="banner-content">
        <div class="banner-title">FlotteManager</div>
        <div class="banner-subtitle">Système de gestion de flotte de véhicules</div>
        <div class="mt-4">
            <span class="badge-stat green">${nbMissionsEnCours} mission(s) en cours</span>
            <span class="badge-stat yellow">${nbChauffeursDispos} chauffeur(s) disponible(s)</span>
        </div>
        <a href="#modules" class="btn btn-outline-light mt-4 px-4">Accéder à l'application</a>
    </div>
    <div class="scroll-hint">&#8964;</div>
</div>

<%-- ── Cartes modules ──────────────────────────────────────────── --%>
<section class="cards-section" id="modules">
    <div class="container">

        <div class="row g-4">

            <%-- Missions --%>
            <div class="col-md-4">
                <a href="${pageContext.request.contextPath}/missions" class="feature-card">
                    <div class="bg-img" style="background-image: url('${pageContext.request.contextPath}/assets/missions.jpg')"></div>
                    <div class="card-dim"></div>
                    <div class="card-body-custom">
                        <div class="card-title-custom">Missions</div>
                        <div class="card-desc">Planification, affectation et suivi des missions</div>
                        <span class="badge-stat">${totalMissions} missions</span>
                        <span class="badge-stat yellow">${nbMissionsEnCours} en cours</span>
                    </div>
                </a>
            </div>

            <%-- Chauffeurs --%>
            <div class="col-md-4">
                <a href="${pageContext.request.contextPath}/chauffeurs" class="feature-card">
                    <div class="bg-img" style="background-image: url('${pageContext.request.contextPath}/assets/chauffeurs.jpg')"></div>
                    <div class="card-dim"></div>
                    <div class="card-body-custom">
                        <div class="card-title-custom">Chauffeurs</div>
                        <div class="card-desc">Gestion des chauffeurs, permis et disponibilités</div>
                        <span class="badge-stat">${totalChauffeurs} chauffeurs</span>
                        <span class="badge-stat green">${nbChauffeursDispos} disponibles</span>
                    </div>
                </a>
            </div>

            <%-- Statistiques --%>
            <div class="col-md-4">
                <a href="${pageContext.request.contextPath}/statistiques" class="feature-card">
                    <div class="bg-img" style="background-image: url('${pageContext.request.contextPath}/assets/statistiques.jpg')"></div>
                    <div class="card-dim"></div>
                    <div class="card-body-custom">
                        <div class="card-title-custom">Statistiques</div>
                        <div class="card-desc">Indicateurs, taux de disponibilité et tableaux de bord</div>
                    </div>
                </a>
            </div>

            <%-- Véhicules --%>
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/vehicules" class="feature-card">
                    <div class="bg-img" style="background-image: url('${pageContext.request.contextPath}/assets/vehicules.jpg')"></div>
                    <div class="card-dim"></div>
                    <div class="card-body-custom">
                        <div class="card-title-custom">Véhicules</div>
                        <div class="card-desc">Gestion du parc, immatriculations, états et kilométrages</div>
                    </div>
                </a>
            </div>

            <%-- Incidents --%>
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/incidents" class="feature-card">
                    <div class="bg-img" style="background-image: url('${pageContext.request.contextPath}/assets/incidents.jpg')"></div>
                    <div class="card-dim"></div>
                    <div class="card-body-custom">
                        <div class="card-title-custom">Incidents</div>
                        <div class="card-desc">Déclaration, suivi des incidents et maintenances</div>
                    </div>
                </a>
            </div>

        </div>
    </div>
</section>

<footer>
    FlotteManager &copy; 2026 &mdash; Gestion de flotte de véhicules
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>