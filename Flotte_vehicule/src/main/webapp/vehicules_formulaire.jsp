<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire véhicule</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>

<div class="container mt-5">

    <div class="card shadow">

        <div class="card-header bg-dark text-white">
            <h3>
                ${vehicule == null ? 'Ajouter un véhicule' : 'Modifier un véhicule'}
            </h3>
        </div>

        <div class="card-body">

            <c:if test="${not empty erreur}">
                <div class="alert alert-danger">
                        ${erreur}
                </div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/vehicules">

                <input type="hidden"
                       name="action"
                       value="${vehicule == null ? 'creer' : 'modifier'}">

                <div class="mb-3">

                    <label class="form-label">Immatriculation</label>

                    <input type="text"
                           class="form-control"
                           name="immatriculation"
                           required>

                </div>

                <div class="mb-3">

                    <label class="form-label">Marque</label>

                    <input type="text"
                           class="form-control"
                           name="marque"
                           required>

                </div>

                <div class="mb-3">

                    <label class="form-label">Modèle</label>

                    <input type="text"
                           class="form-control"
                           name="modele"
                           required>

                </div>

                <div class="mb-3">

                    <label class="form-label">Kilométrage</label>

                    <input type="number"
                           class="form-control"
                           name="kilometrage"
                           required>

                </div>

                <div class="mb-3">

                    <label class="form-label">Type</label>

                    <select class="form-select"
                            name="type"
                            id="typeVehicule"
                            onchange="changerType()">

                        <option value="LEGER">Véhicule léger</option>
                        <option value="LOURD">Véhicule lourd</option>
                        <option value="SPECIAL">Véhicule spécial</option>

                    </select>

                </div>

                <!-- LEGER -->

                <div id="blocLeger">

                    <div class="mb-3">

                        <label class="form-label">
                            Nombre de places
                        </label>

                        <input type="number"
                               class="form-control"
                               name="nombrePlaces">

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Consommation
                        </label>

                        <input type="number"
                               step="0.1"
                               class="form-control"
                               name="consommation">

                    </div>

                </div>

                <!-- LOURD -->

                <div id="blocLourd" style="display:none;">

                    <div class="mb-3">

                        <label class="form-label">
                            Capacité de charge
                        </label>

                        <input type="number"
                               step="0.1"
                               class="form-control"
                               name="capaciteCharge">

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Nombre d'essieux
                        </label>

                        <input type="number"
                               class="form-control"
                               name="nombreEssieux">

                    </div>

                </div>

                <!-- SPECIAL -->

                <div id="blocSpecial" style="display:none;">

                    <div class="mb-3">

                        <label class="form-label">
                            Type spécial
                        </label>

                        <input type="text"
                               class="form-control"
                               name="typeSpecial">

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Position GPS
                        </label>

                        <input type="text"
                               class="form-control"
                               name="positionGPS">

                    </div>

                    <div class="form-check">

                        <input class="form-check-input"
                               type="checkbox"
                               name="modeUrgence">

                        <label class="form-check-label">
                            Mode urgence
                        </label>

                    </div>

                </div>

                <button class="btn btn-primary">
                    Enregistrer
                </button>

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/vehicules">

                    Annuler

                </a>

            </form>

        </div>

    </div>

</div>

<script>

    function changerType() {

        let type =
            document.getElementById("typeVehicule").value;

        document.getElementById("blocLeger")
            .style.display = "none";

        document.getElementById("blocLourd")
            .style.display = "none";

        document.getElementById("blocSpecial")
            .style.display = "none";

        if (type === "LEGER") {

            document.getElementById("blocLeger")
                .style.display = "block";
        }

        if (type === "LOURD") {

            document.getElementById("blocLourd")
                .style.display = "block";
        }

        if (type === "SPECIAL") {

            document.getElementById("blocSpecial")
                .style.display = "block";
        }
    }

</script>

</body>
</html>