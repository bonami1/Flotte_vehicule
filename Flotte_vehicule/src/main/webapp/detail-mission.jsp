<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Detail Mission — Flotte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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

<div class="container">
    <a href="${pageContext.request.contextPath}/missions" class="btn btn-outline-secondary mb-3">
        &larr; Retour aux missions
    </a>

    <c:if test="${not empty flashMessage}">
        <div class="alert alert-${flashType} alert-dismissible fade show" role="alert">
            ${flashMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="row g-4">
        <%-- Carte details --%>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0">Mission ${mission.id}</h5>
                </div>
                <div class="card-body">
                    <table class="table table-borderless mb-0">
                        <tr>
                            <th>Type</th>
                            <td>
                                <span class="badge ${mission.type eq 'Longue' ? 'bg-primary' : 'bg-secondary'}">
                                    ${mission.type}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th>Itineraire</th>
                            <td>${mission.itineraire}</td>
                        </tr>
                        <tr>
                            <th>Statut</th>
                            <td>
                                <span class="badge ${mission.statut eq 'En cours' ? 'bg-warning text-dark' : 'bg-success'}">
                                    ${mission.statut}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th>Date debut</th>
                            <td>${mission.dateDebut}</td>
                        </tr>
                        <tr>
                            <th>Chauffeur</th>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty mission.chauffeurAssigne}">
                                        ${mission.chauffeurAssigne.prenom} ${mission.chauffeurAssigne.nom}
                                        (${mission.chauffeurAssigne.permis})
                                    </c:when>
                                    <c:otherwise><span class="text-muted">Non assigne</span></c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <%-- Rapport --%>
            <div class="card mt-3">
                <div class="card-header">Rapport</div>
                <div class="card-body">
                    <p class="mb-0 fst-italic">${mission.genererRapport()}</p>
                </div>
            </div>
        </div>

        <%-- Actions --%>
        <div class="col-md-6">
            <c:if test="${mission.statut eq 'En cours'}">

                <%-- Affecter un chauffeur --%>
                <div class="card mb-3">
                    <div class="card-header bg-primary text-white">Affecter un chauffeur</div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty chauffeursDisponibles}">
                                <form method="post" action="${pageContext.request.contextPath}/missions">
                                    <input type="hidden" name="action" value="affecter">
                                    <input type="hidden" name="id" value="${mission.id}">
                                    <div class="mb-3">
                                        <select name="chauffeurId" class="form-select" required>
                                            <option value="">-- Choisir un chauffeur --</option>
                                            <c:forEach var="c" items="${chauffeursDisponibles}">
                                                <option value="${c.id}">${c.prenom} ${c.nom} (${c.permis})</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Affecter</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted mb-0">Aucun chauffeur disponible pour le moment.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <%-- Terminer la mission --%>
                <div class="card">
                    <div class="card-header bg-success text-white">Terminer la mission</div>
                    <div class="card-body">
                        <p class="text-muted">Cette action est irreversible. Le chauffeur sera libere.</p>
                        <form method="post" action="${pageContext.request.contextPath}/missions"
                              onsubmit="return confirm('Terminer cette mission ?')">
                            <input type="hidden" name="action" value="terminer">
                            <input type="hidden" name="id" value="${mission.id}">
                            <button type="submit" class="btn btn-success w-100">Marquer comme terminee</button>
                        </form>
                    </div>
                </div>
            </c:if>

            <c:if test="${mission.statut ne 'En cours'}">
                <div class="alert alert-success">
                    Cette mission est terminee. Aucune action disponible.
                </div>
            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>