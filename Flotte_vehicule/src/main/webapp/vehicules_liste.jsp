<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Véhicules</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
        rel="stylesheet">
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

<div class="container">

  <div class="d-flex justify-content-between mb-3">

    <h2>Liste des véhicules</h2>

    <a class="btn btn-primary"
       href="${pageContext.request.contextPath}/vehicules?action=formulaire">
      + Ajouter
    </a>

  </div>

  <c:if test="${not empty param.succes}">
    <div class="alert alert-success">
      Opération réussie
    </div>
  </c:if>

  <table class="table table-striped table-hover">

    <thead class="table-dark">

    <tr>
      <th>Immatriculation</th>
      <th>Marque</th>
      <th>Modèle</th>
      <th>Km</th>
      <th>État</th>
      <th>Actions</th>
    </tr>

    </thead>

    <tbody>

    <c:forEach items="${vehicules}" var="v">

      <tr>

        <td>${v.immatriculation}</td>
        <td>${v.marque}</td>
        <td>${v.modele}</td>
        <td>${v.kilometrage}</td>
        <td>${v.etat}</td>

        <td>

          <a class="btn btn-sm btn-info"
             href="${pageContext.request.contextPath}/vehicules?action=detail&immat=${v.immatriculation}">
            Voir
          </a>

          <a class="btn btn-sm btn-warning"
             href="${pageContext.request.contextPath}/vehicules?action=formulaire&immat=${v.immatriculation}">
            Modifier
          </a>

          <a class="btn btn-sm btn-danger"
             onclick="return confirm('Supprimer ?')"
             href="${pageContext.request.contextPath}/vehicules?action=supprimer&immat=${v.immatriculation}">
            Supprimer
          </a>

        </td>

      </tr>

    </c:forEach>

    </tbody>

  </table>

</div>

</body>
</html>