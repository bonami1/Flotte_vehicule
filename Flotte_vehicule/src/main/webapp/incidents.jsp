<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Incidents — Flotte</title>
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
    <h2 class="mb-3">Gestion des Incidents</h2>

    <c:if test="${not empty flashMessage}">
        <div class="alert alert-${flashType} alert-dismissible fade show" role="alert">
            ${flashMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <%-- Filtres --%>
    <form method="get" action="${pageContext.request.contextPath}/incidents" class="card mb-3">
        <div class="card-body">
            <div class="row g-2 align-items-end">
                <div class="col-md-2">
                    <label class="form-label fw-semibold">Type</label>
                    <select name="filtreType" class="form-select">
                        <option value="">Tous</option>
                        <option value="Panne"    ${filtreType eq 'Panne'    ? 'selected' : ''}>Panne</option>
                        <option value="Accident" ${filtreType eq 'Accident' ? 'selected' : ''}>Accident</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-semibold">Traite</label>
                    <select name="filtreTraite" class="form-select">
                        <option value="">Tous</option>
                        <option value="non" ${filtreTraite eq 'non' ? 'selected' : ''}>Non traite</option>
                        <option value="oui" ${filtreTraite eq 'oui' ? 'selected' : ''}>Traite</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label fw-semibold">Vehicule ID</label>
                    <input type="text" name="filtreVehicule" class="form-control"
                           placeholder="Ex: VEH-001" value="${filtreVehicule}">
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-semibold">Trier par</label>
                    <select name="tri" class="form-select">
                        <option value="">—</option>
                        <option value="date"   ${tri eq 'date'   ? 'selected' : ''}>Date</option>
                        <option value="type"   ${tri eq 'type'   ? 'selected' : ''}>Type</option>
                        <option value="cout"   ${tri eq 'cout'   ? 'selected' : ''}>Cout</option>
                        <option value="traite" ${tri eq 'traite' ? 'selected' : ''}>Traite</option>
                    </select>
                </div>
                <div class="col-md-1">
                    <label class="form-label fw-semibold">Ordre</label>
                    <select name="ordre" class="form-select">
                        <option value="asc"  ${ordre eq 'asc'  ? 'selected' : ''}>A-Z</option>
                        <option value="desc" ${ordre eq 'desc' ? 'selected' : ''}>Z-A</option>
                    </select>
                </div>
                <div class="col-md-1">
                    <button type="submit" class="btn btn-primary w-100">Filtrer</button>
                </div>
                <div class="col-md-1">
                    <a href="${pageContext.request.contextPath}/incidents" class="btn btn-outline-secondary w-100">Reset</a>
                </div>
            </div>
        </div>
    </form>

    <%-- Formulaire ajout --%>
    <button class="btn btn-danger mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#formAjout">
        + Declarer un incident
    </button>
    <div class="collapse mb-3" id="formAjout">
        <div class="card card-body border-danger">
            <form method="post" action="${pageContext.request.contextPath}/incidents">
                <input type="hidden" name="action" value="create">
                <div class="row g-2">
                    <div class="col-md-2">
                        <label class="form-label">Type *</label>
                        <select name="type" class="form-select" onchange="toggleChamps(this.value)">
                            <option value="Panne">Panne mecanique</option>
                            <option value="Accident">Accident</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Description *</label>
                        <input type="text" name="description" class="form-control"
                               placeholder="Ex: Pneu creve" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Cout estime (EUR) *</label>
                        <input type="number" name="coutEstime" class="form-control"
                               placeholder="0.00" min="0" step="0.01" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Vehicule ID</label>
                        <input type="text" name="vehiculeId" class="form-control"
                               placeholder="Ex: VEH-001">
                    </div>
                    <div class="col-md-2" id="champPanne">
                        <label class="form-label">Type de panne</label>
                        <select name="typePanne" class="form-select">
                            <option value="Moteur">Moteur</option>
                            <option value="Freins">Freins</option>
                            <option value="Pneu">Pneu</option>
                            <option value="Electrique">Electrique</option>
                            <option value="Autre">Autre</option>
                        </select>
                    </div>
                    <div class="col-md-2" id="champAccident" style="display:none">
                        <label class="form-label">Gravite</label>
                        <select name="gravite" class="form-select">
                            <option value="Leger">Leger</option>
                            <option value="Grave">Grave</option>
                        </select>
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-danger w-100">Declarer</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <%-- Tableau --%>
    <div class="table-responsive">
        <table class="table table-striped table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>Type</th>
                <th>Description</th>
                <th>Detail</th>
                <th>Vehicule</th>
                <th>Date</th>
                <th>Cout estime</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="incident" items="${listeIncidents}">
                <tr>
                    <td>
                        <span class="badge ${incident.type eq 'Accident' ? 'bg-danger' : 'bg-warning text-dark'}">
                            ${incident.type}
                        </span>
                    </td>
                    <td>${incident.description}</td>
                    <td class="text-muted fst-italic" style="font-size:0.85rem">
                        <c:choose>
                            <c:when test="${incident.type eq 'Accident'}">${incident.gravite}</c:when>
                            <c:otherwise>${incident.typePanne}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty incident.vehiculeId}">${incident.vehiculeId}</c:when>
                            <c:otherwise><span class="text-muted fst-italic">—</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${incident.date}</td>
                    <td>${incident.coutEstime} EUR</td>
                    <td>
                        <span class="badge ${incident.traite ? 'bg-success' : 'bg-secondary'}">
                            ${incident.traite ? 'Traite' : 'En attente'}
                        </span>
                    </td>
                    <td>
                        <c:if test="${not incident.traite}">
                            <form method="post" action="${pageContext.request.contextPath}/incidents"
                                  style="display:inline">
                                <input type="hidden" name="action" value="traiter">
                                <input type="hidden" name="id" value="${incident.id}">
                                <button type="submit" class="btn btn-sm btn-outline-success">Traiter</button>
                            </form>
                        </c:if>
                        <form method="post" action="${pageContext.request.contextPath}/incidents"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer cet incident ?')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${incident.id}">
                            <button type="submit" class="btn btn-sm btn-outline-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty listeIncidents}">
                <tr>
                    <td colspan="8" class="text-center text-muted py-3">Aucun incident enregistre.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
    <p class="text-muted">${fn:length(listeIncidents)} incident(s) affiche(s)</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function toggleChamps(type) {
    document.getElementById('champPanne').style.display    = type === 'Panne'    ? 'block' : 'none';
    document.getElementById('champAccident').style.display = type === 'Accident' ? 'block' : 'none';
}
</script>
</body>
</html>