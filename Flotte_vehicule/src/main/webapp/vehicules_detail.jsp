<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détail véhicule</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>

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