<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques — Flotte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .stat-card { border-left: 5px solid; }
        .stat-card.blue  { border-color: #0d6efd; }
        .stat-card.green { border-color: #198754; }
        .stat-card.yellow{ border-color: #ffc107; }
        .stat-card.red   { border-color: #dc3545; }
        .stat-number { font-size: 2.5rem; font-weight: 700; }
        .progress { height: 24px; }
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
            <a class="nav-link active" href="${pageContext.request.contextPath}/statistiques">Statistiques</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="mb-4">Tableau de bord</h2>

    <%-- Statistiques missions --%>
    <h5 class="text-muted mb-3">Missions</h5>
    <div class="row g-3 mb-4">
        <div class="col-md-3">
            <div class="card stat-card blue p-3">
                <div class="text-muted small">Total missions</div>
                <div class="stat-number text-primary">${totalMissions}</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card yellow p-3">
                <div class="text-muted small">En cours</div>
                <div class="stat-number text-warning">${nbEnCours}</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card green p-3">
                <div class="text-muted small">Terminees</div>
                <div class="stat-number text-success">${nbTerminees}</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card blue p-3">
                <div class="text-muted small">Longues / Courtes</div>
                <div class="stat-number text-primary">${nbLongues} / ${nbCourtes}</div>
            </div>
        </div>
    </div>

    <%-- Statistiques chauffeurs --%>
    <h5 class="text-muted mb-3">Chauffeurs</h5>
    <div class="row g-3 mb-4">
        <div class="col-md-3">
            <div class="card stat-card blue p-3">
                <div class="text-muted small">Total chauffeurs</div>
                <div class="stat-number text-primary">${totalChauffeurs}</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card green p-3">
                <div class="text-muted small">Disponibles</div>
                <div class="stat-number text-success">${nbChauffeursDispos}</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card red p-3">
                <div class="text-muted small">Occupes</div>
                <div class="stat-number text-danger">${nbChauffeursOccupes}</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card p-3">
                <div class="text-muted small mb-2">Taux de disponibilite</div>
                <div class="progress">
                    <div class="progress-bar bg-success" style="width: ${tauxDispo}%">
                        ${tauxDispo}%
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%-- Repartition par statut --%>
    <h5 class="text-muted mb-3">Repartition des missions par statut</h5>
    <div class="card">
        <div class="card-body">
            <c:forEach var="entry" items="${statsParStatut}">
                <div class="d-flex align-items-center mb-2">
                    <div style="width: 120px" class="fw-semibold">${entry.key}</div>
                    <div class="flex-grow-1 me-3">
                        <c:set var="pct" value="${totalMissions > 0 ? entry.value * 100 / totalMissions : 0}" />
                        <div class="progress">
                            <div class="progress-bar ${entry.key eq 'En cours' ? 'bg-warning' : 'bg-success'}"
                                 style="width: ${pct}%"></div>
                        </div>
                    </div>
                    <div class="text-muted">${entry.value} mission(s)</div>
                </div>
            </c:forEach>
            <c:if test="${empty statsParStatut}">
                <p class="text-muted mb-0">Aucune donnee disponible.</p>
            </c:if>
        </div>
    </div>

    <div class="mt-4 text-end">
        <a href="${pageContext.request.contextPath}/missions" class="btn btn-outline-primary me-2">
            Voir les missions
        </a>
        <a href="${pageContext.request.contextPath}/chauffeurs" class="btn btn-outline-secondary">
            Voir les chauffeurs
        </a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>