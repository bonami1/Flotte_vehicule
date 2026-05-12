<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Missions — Flotte</title>
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
    <h2 class="mb-3">Gestion des Missions</h2>

    <c:if test="${not empty flashMessage}">
        <div class="alert alert-${flashType} alert-dismissible fade show" role="alert">
            ${flashMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <%-- Filtres --%>
    <form method="get" action="${pageContext.request.contextPath}/missions" class="card mb-3">
        <div class="card-body">
            <div class="row g-2 align-items-end">
                <div class="col-md-3">
                    <label class="form-label fw-semibold">Statut</label>
                    <select name="filtreStatut" class="form-select">
                        <option value="">Tous</option>
                        <option value="En cours"  ${filtreStatut eq 'En cours'  ? 'selected' : ''}>En cours</option>
                        <option value="Terminee"  ${filtreStatut eq 'Terminee'  ? 'selected' : ''}>Terminee</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label fw-semibold">Type</label>
                    <select name="filtreType" class="form-select">
                        <option value="">Tous</option>
                        <option value="Courte" ${filtreType eq 'Courte' ? 'selected' : ''}>Courte</option>
                        <option value="Longue" ${filtreType eq 'Longue' ? 'selected' : ''}>Longue</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label fw-semibold">Trier par</label>
                    <select name="tri" class="form-select">
                        <option value="">—</option>
                        <option value="statut"     ${tri eq 'statut'     ? 'selected' : ''}>Statut</option>
                        <option value="type"       ${tri eq 'type'       ? 'selected' : ''}>Type</option>
                        <option value="itineraire" ${tri eq 'itineraire' ? 'selected' : ''}>Itineraire</option>
                        <option value="date"       ${tri eq 'date'       ? 'selected' : ''}>Date</option>
                    </select>
                </div>
                <div class="col-md-1">
                    <label class="form-label fw-semibold">Ordre</label>
                    <select name="ordre" class="form-select">
                        <option value="asc"  ${ordre eq 'asc'  ? 'selected' : ''}>A-Z</option>
                        <option value="desc" ${ordre eq 'desc' ? 'selected' : ''}>Z-A</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Filtrer</button>
                </div>
                <div class="col-md-1">
                    <a href="${pageContext.request.contextPath}/missions" class="btn btn-outline-secondary w-100">Reset</a>
                </div>
            </div>
        </div>
    </form>

    <%-- Formulaire ajout --%>
    <button class="btn btn-success mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#formAjout">
        + Ajouter une mission
    </button>
    <div class="collapse mb-3" id="formAjout">
        <div class="card card-body border-success">
            <form method="post" action="${pageContext.request.contextPath}/missions">
                <input type="hidden" name="action" value="create">
                <div class="row g-2">
                    <div class="col-md-4">
                        <label class="form-label">Itineraire *</label>
                        <input type="text" name="itineraire" class="form-control"
                               placeholder="Ex: Paris - Lyon" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Type *</label>
                        <select name="type" class="form-select" onchange="togglePauses(this.value)">
                            <option value="courte">Courte</option>
                            <option value="longue">Longue</option>
                        </select>
                    </div>
                    <div class="col-md-3" id="pausesDiv" style="display:none">
                        <label class="form-label">Nombre de pauses</label>
                        <input type="number" name="nombrePauses" class="form-control" value="0" min="0">
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-success w-100">Creer</button>
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
                <th>Itineraire</th>
                <th>Statut</th>
                <th>Chauffeur</th>
                <th>Date debut</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="mission" items="${listeMissions}">
                <tr>
                    <td>
                        <span class="badge ${mission.type eq 'Longue' ? 'bg-primary' : 'bg-secondary'}">
                            ${mission.type}
                        </span>
                    </td>
                    <td>${mission.itineraire}</td>
                    <td>
                        <span class="badge ${mission.statut eq 'En cours' ? 'bg-warning text-dark' : 'bg-success'}">
                            ${mission.statut}
                        </span>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty mission.chauffeurAssigne}">
                                ${mission.chauffeurAssigne.prenom} ${mission.chauffeurAssigne.nom}
                            </c:when>
                            <c:otherwise><span class="text-muted fst-italic">Non assigne</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${mission.dateDebut}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/missions?id=${mission.id}"
                           class="btn btn-sm btn-outline-info">Detail</a>
                        <form method="post" action="${pageContext.request.contextPath}/missions"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer cette mission ?')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${mission.id}">
                            <button type="submit" class="btn btn-sm btn-outline-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty listeMissions}">
                <tr>
                    <td colspan="6" class="text-center text-muted py-3">Aucune mission trouvee.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
    <p class="text-muted">${fn:length(listeMissions)} mission(s) affichee(s)</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function togglePauses(type) {
    document.getElementById('pausesDiv').style.display = type === 'longue' ? 'block' : 'none';
}
</script>
</body>
</html>