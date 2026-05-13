<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques véhicules</title>

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

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">FlotteManager</a>
        <div class="navbar-nav ms-3">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Accueil</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/missions">Missions</a>
            <a class="nav-link active" href="${pageContext.request.contextPath}/chauffeurs">Chauffeurs</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/vehicules">Vehicules</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/incidents">Incidents</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/statistiques">Statistiques</a>
        </div>
    </div>
</nav>

<!-- ================= CONTENT ================= -->
<div class="container mt-4">

    <h2 class="mb-4">Statistiques des véhicules</h2>

    <!-- ================= CARDS ================= -->
    <div class="row g-3 mb-4">

        <div class="col-md-3">
            <div class="card text-center border-success">
                <div class="card-body">
                    <h3 class="text-success">${nbDisponibles}</h3>
                    <p class="mb-0">Disponibles</p>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center border-warning">
                <div class="card-body">
                    <h3 class="text-warning">${nbEnMission}</h3>
                    <p class="mb-0">En mission</p>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center border-danger">
                <div class="card-body">
                    <h3 class="text-danger">${nbMaintenance}</h3>
                    <p class="mb-0">Maintenance</p>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center border-primary">
                <div class="card-body">
                    <h3 class="text-primary">${kmMoyen}</h3>
                    <p class="mb-0">Km moyen</p>
                </div>
            </div>
        </div>

    </div>

    <!-- ================= VEHICULE MAX ================= -->
    <div class="card mb-4">

        <div class="card-header bg-dark text-white">
            Véhicule le plus kilométré
        </div>

        <div class="card-body">

            <c:if test="${not empty vehiculeKmMax}">

                <p><b>Immatriculation :</b> ${vehiculeKmMax.immatriculation}</p>
                <p><b>Marque :</b> ${vehiculeKmMax.marque}</p>
                <p><b>Modèle :</b> ${vehiculeKmMax.modele}</p>
                <p><b>Kilométrage :</b> ${vehiculeKmMax.kilometrage} km</p>

            </c:if>

        </div>

    </div>

    <!-- ================= CHART ================= -->
    <div class="card">

        <div class="card-header bg-dark text-white">
            Répartition des états
        </div>

        <div class="card-body">

            <canvas id="chart" style="max-height:300px;"></canvas>

        </div>

    </div>

</div>

<!-- ================= SCRIPT CHART ================= -->
<script>

    new Chart(document.getElementById("chart"), {

        type: "pie",

        data: {

            labels: [
                "Disponibles",
                "En mission",
                "Maintenance"
            ],

            datasets: [{

                data: [
                    ${nbDisponibles},
                    ${nbEnMission},
                    ${nbMaintenance}
                ],

                backgroundColor: [
                    "#198754",
                    "#ffc107",
                    "#dc3545"
                ]

            }]

        }

    });

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>