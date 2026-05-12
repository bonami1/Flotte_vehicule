<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion des Missions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h2>Chauffeurs disponibles pour mission</h2>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Nom</th>
        <th>Permis</th>
        <th>Statut</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="chauffeur" items="${chauffeursDispo}">
        <tr>
            <td>${chauffeur.nom}</td>
            <td>${chauffeur.permis}</td>
            <td><span class="badge bg-success">Disponible</span></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>