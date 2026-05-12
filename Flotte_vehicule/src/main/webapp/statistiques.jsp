<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">FlotteManager</a>
        <div class="navbar-nav ms-3">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Accueil</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/missions">Missions</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/chauffeurs">Chauffeurs</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/incidents">Incidents</a>
            <a class="nav-link active" href="${pageContext.request.contextPath}/statistiques">Statistiques</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="mb-4">Statistiques</h2>

    <%-- Chiffres clés --%>
    <div class="row mb-5">

        <div class="col-md-3 mb-3">
            <div class="card text-center border-primary">
                <div class="card-body">
                    <h3 class="text-primary">${totalMissions}</h3>
                    <p class="mb-0">Missions au total</p>
                </div>
            </div>
        </div>

        <div class="col-md-3 mb-3">
            <div class="card text-center border-warning">
                <div class="card-body">
                    <h3 class="text-warning">${nbEnCours}</h3>
                    <p class="mb-0">Missions en cours</p>
                </div>
            </div>
        </div>

        <div class="col-md-3 mb-3">
            <div class="card text-center border-success">
                <div class="card-body">
                    <h3 class="text-success">${nbTerminees}</h3>
                    <p class="mb-0">Missions terminees</p>
                </div>
            </div>
        </div>

        <div class="col-md-3 mb-3">
            <div class="card text-center border-info">
                <div class="card-body">
                    <h3 class="text-info">${nbChauffeursDispos}</h3>
                    <p class="mb-0">Chauffeurs disponibles</p>
                </div>
            </div>
        </div>

    </div>

    <%-- Graphiques --%>
    <div class="row">

        <div class="col-md-6 mb-4">
            <div class="card">
                <div class="card-header">Statut des missions</div>
                <div class="card-body">
                    <canvas id="graphStatut" style="max-height: 250px;"></canvas>
                </div>
            </div>
        </div>

        <div class="col-md-6 mb-4">
            <div class="card">
                <div class="card-header">Missions courtes vs longues</div>
                <div class="card-body">
                    <canvas id="graphType" style="max-height: 250px;"></canvas>
                </div>
            </div>
        </div>

    </div>
</div>

<script>
    new Chart(document.getElementById('graphStatut'), {
        type: 'pie',
        data: {
            labels: ['En cours', 'Terminees'],
            datasets: [{
                data: [${nbEnCours}, ${nbTerminees}],
                backgroundColor: ['#ffc107', '#198754']
            }]
        }
    });

    new Chart(document.getElementById('graphType'), {
        type: 'bar',
        data: {
            labels: ['Courtes', 'Longues'],
            datasets: [{
                label: 'Nombre de missions',
                data: [${nbCourtes}, ${nbLongues}],
                backgroundColor: ['#0d6efd', '#6c757d']
            }]
        },
        options: {
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>