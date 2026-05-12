<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques véhicules</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<div class="container mt-4">

    <h2>Statistiques</h2>

    <div class="row mt-4">

        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h4>${nbDisponibles}</h4>
                    <p>Disponibles</p>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h4>${nbEnMission}</h4>
                    <p>En mission</p>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h4>${nbMaintenance}</h4>
                    <p>Maintenance</p>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h4>${kmMoyen}</h4>
                    <p>Km moyen</p>
                </div>
            </div>
        </div>

    </div>

    <div class="mt-5">

        <canvas id="chart"></canvas>

    </div>

</div>

<script>

    new Chart(document.getElementById("chart"), {

        type: "pie",

        data: {

            labels: ["Disponibles", "Mission", "Maintenance"],

            datasets: [{

                data: [
                    ${nbDisponibles},
                    ${nbEnMission},
                    ${nbMaintenance}
                ],

                backgroundColor: ["green", "orange", "red"]

            }]

        }

    });

</script>

</body>
</html>