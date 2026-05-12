<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Chauffeurs — Flotte</title>
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
            <a class="nav-link" href="${pageContext.request.contextPath}/statistiques">Statistiques</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="mb-3">Gestion des Chauffeurs</h2>

    <c:if test="${not empty flashMessage}">
        <div class="alert alert-${flashType} alert-dismissible fade show" role="alert">
            ${flashMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <%-- Formulaire ajout --%>
    <button class="btn btn-success mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#formAjout">
        + Ajouter un chauffeur
    </button>
    <div class="collapse mb-3" id="formAjout">
        <div class="card card-body border-success">
            <form method="post" action="${pageContext.request.contextPath}/chauffeurs">
                <input type="hidden" name="action" value="create">
                <div class="row g-2">
                    <div class="col-md-3">
                        <label class="form-label">Nom *</label>
                        <input type="text" name="nom" class="form-control" placeholder="Nom" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Prenom *</label>
                        <input type="text" name="prenom" class="form-control" placeholder="Prenom" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Permis</label>
                        <select name="permis" class="form-select">
                            <option value="Permis B">Permis B</option>
                            <option value="Permis C">Permis C</option>
                            <option value="Permis D">Permis D</option>
                            <option value="Permis BE">Permis BE</option>
                        </select>
                    </div>
                    <div class="col-md-3 d-flex align-items-end">
                        <button type="submit" class="btn btn-success w-100">Ajouter</button>
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
                <th>Prenom</th>
                <th>Nom</th>
                <th>Permis</th>
                <th>Disponibilite</th>
                <th>Missions</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="chauffeur" items="${listeChauffeurs}">
                <tr>
                    <td>${chauffeur.prenom}</td>
                    <td>${chauffeur.nom}</td>
                    <td><span class="badge bg-info text-dark">${chauffeur.permis}</span></td>
                    <td>
                        <c:choose>
                            <c:when test="${chauffeur.estDisponible()}">
                                <span class="badge bg-success">Disponible</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">Occupe</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${fn:length(chauffeur.historiqueMissions)} mission(s)</td>
                    <td>
                        <%-- Bouton modifier --%>
                        <button class="btn btn-sm btn-outline-warning"
                                data-bs-toggle="modal"
                                data-bs-target="#modalEdit"
                                data-id="${chauffeur.id}"
                                data-nom="${chauffeur.nom}"
                                data-prenom="${chauffeur.prenom}"
                                data-permis="${chauffeur.permis}">
                            Modifier
                        </button>
                        <%-- Bouton supprimer --%>
                        <form method="post" action="${pageContext.request.contextPath}/chauffeurs"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer ce chauffeur ?')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${chauffeur.id}">
                            <button type="submit" class="btn btn-sm btn-outline-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty listeChauffeurs}">
                <tr>
                    <td colspan="6" class="text-center text-muted py-3">Aucun chauffeur enregistre.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
    <p class="text-muted">${fn:length(listeChauffeurs)} chauffeur(s)</p>
</div>

<%-- Modal modification --%>
<div class="modal fade" id="modalEdit" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modifier le chauffeur</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/chauffeurs">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" id="editId">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Nom</label>
                        <input type="text" name="nom" id="editNom" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Prenom</label>
                        <input type="text" name="prenom" id="editPrenom" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Permis</label>
                        <select name="permis" id="editPermis" class="form-select">
                            <option value="Permis B">Permis B</option>
                            <option value="Permis C">Permis C</option>
                            <option value="Permis D">Permis D</option>
                            <option value="Permis BE">Permis BE</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-warning">Enregistrer</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.getElementById('modalEdit').addEventListener('show.bs.modal', function(event) {
    var btn = event.relatedTarget;
    document.getElementById('editId').value     = btn.getAttribute('data-id');
    document.getElementById('editNom').value    = btn.getAttribute('data-nom');
    document.getElementById('editPrenom').value = btn.getAttribute('data-prenom');
    document.getElementById('editPermis').value = btn.getAttribute('data-permis');
});
</script>
</body>
</html>