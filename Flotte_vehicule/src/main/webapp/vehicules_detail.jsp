<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détail véhicule</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <style>

        .feature-card {
            position: relative;
            height: 200px;
            border-radius: 12px;
            overflow: hidden;
            display: block;
            text-decoration: none;
            transition: transform 0.3s ease;
        }

        .feature-card:hover {
            transform: translateY(-6px);
        }

        .feature-card .bg-img {
            position: absolute;
            inset: 0;
            background-size: cover;
            background-position: center;
        }

        .feature-card .card-dim {
            position: absolute;
            inset: 0;
            background: rgba(0,0,0,0.5);
        }

        .feature-card .card-body-custom {
            position: absolute;
            bottom: 0;
            padding: 1rem;
            color: white;
        }

        .card-title-custom {
            font-size: 1.3rem;
            font-weight: bold;
        }

        .card-desc {
            font-size: 0.85rem;
            opacity: 0.9;
        }

    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">FlotteManager</a>
        <div class="navbar-nav ms-3">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Accueil</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/missions">Missions</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/chauffeurs">Chauffeurs</a>
            <a class="nav-link active" href="${pageContext.request.contextPath}/vehicules">Véhicules</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/incidents">Incidents</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/statistiques">Statistiques</a>
        </div>
    </div>
</nav>

<div class="container mt-5">

    <div class="card">

        <div class="card-header bg-dark text-white">
            Détail véhicule
        </div>

        <div class="card-body">

            <p><b>Immatriculation :</b> ${vehicule.immatriculation}</p>
            <p><b>Marque :</b> ${vehicule.marque}</p>
            <p><b>Modèle :</b> ${vehicule.modele}</p>
            <p><b>Kilométrage :</b> ${vehicule.kilometrage}</p>
            <p><b>État :</b> ${vehicule.etat}</p>

            <hr>

            <a class="btn btn-warning"
               href="${pageContext.request.contextPath}/vehicules?action=formulaire&immat=${vehicule.immatriculation}">
                Modifier
            </a>

            <a class="btn btn-secondary"
               href="${pageContext.request.contextPath}/vehicules">
                Retour
            </a>

        </div>

    </div>

</div>

</body>
</html>