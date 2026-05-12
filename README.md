# FlotteManager — Gestion de flotte de véhicules

**Groupe :**  
HILAIRE Jennifer · AMRANI Malika · BOURAZA Sofia · BONAMI Nirmala

---
## Domaine métier
Gestion de flotte de véhicules

---
## Description

FlotteManager est une application web Java qui permet de gérer une flotte de véhicules.  
Elle couvre la gestion des missions, des chauffeurs, des véhicules et des incidents, avec un tableau de bord statistique.

---

## Technologies utilisées

- Java 11
- Servlet / JSP (application web — Option B)
- Apache Tomcat 7
- Maven
- Bootstrap 5
- Chart.js

---

## Lancer l'application

Dans le terminal IntelliJ :

```
mvn tomcat7:run
```

Puis ouvrir : **http://localhost:8080/flotte/home**

---

## Structure du projet

```
src/
├── main/
│   ├── java/fr/flotte/
│   │   ├── model/        ← Classes métier (Mission, Chauffeur, interfaces...)
│   │   ├── controller/   ← Servlets
│   │   ├── exception/    ← Exceptions custom
│   │   └── util/         ← Persistance
│   └── webapp/
│       ├── assets/       ← Images et vidéo
│       └── *.jsp         ← Pages web
```

---

## Pages de l'application

| URL | Description |
|---|---|
| `/home` | Page d'accueil |
| `/missions` | Gestion des missions |
| `/chauffeurs` | Gestion des chauffeurs |
| `/statistiques` | Tableau de bord |

---

## Choix de la technologie d'interface

Nous avons choisi l'**Option B — Application web (Servlet/JSP)** pour les raisons suivantes :

- **Accessibilité** : l'application est utilisable depuis n'importe quel navigateur, sans installation côté client
- **Séparation des responsabilités** : les Servlets jouent le rôle de contrôleurs et les JSP s'occupent uniquement de l'affichage, ce qui correspond au modèle MVC
- **Facilité de mise en page** : Bootstrap 5 permet de créer une interface propre et responsive rapidement
- **Evolutivité** : l'architecture web facilite l'ajout de nouvelles pages et fonctionnalités sans modifier la logique métier

---

## Fonctionnalités

### Gestion des missions
- Créer une mission courte ou longue (itinéraire, nombre de pauses)
- Lister toutes les missions
- Voir le détail d'une mission et son rapport généré automatiquement
- Affecter un chauffeur disponible à une mission
- Terminer une mission (le chauffeur est automatiquement libéré)
- Supprimer une mission
- Filtrer par statut (en cours / terminée) et par type (courte / longue)
- Trier par itinéraire, statut, type ou date

### Gestion des chauffeurs
- Ajouter un chauffeur (nom, prénom, permis)
- Modifier les informations d'un chauffeur
- Supprimer un chauffeur
- Voir la disponibilité en temps réel
- Consulter le nombre de missions effectuées

### Statistiques
- Nombre total de missions, en cours, terminées
- Répartition missions courtes / longues
- Nombre de chauffeurs disponibles / occupés
- Taux de disponibilité des chauffeurs
- Graphiques interactifs (camembert et barres) avec Chart.js

### Fonctionnalités techniques
- Filtrage multicritères avec `Stream` et `Predicate`
- Tri dynamique sur plusieurs colonnes avec `Comparator`
- Persistance des données par sérialisation Java
- Exceptions métier custom (`ChauffeurIndisponibleException`, `MissionDejaTermineeException`)
- Messages de confirmation et d'erreur après chaque action

---

## Concepts OO utilisés

- **2 classes abstraites** : `Mission`, `Vehicule`
- **3 interfaces** : `Assignable`, `Trackable`, `Maintenable`
- **Générique borné** : `GestionnaireOperationnel<T extends Mission>`
- **Collections** : `List`, `Map`, `PriorityQueue`
- **Streams & Lambdas** : filter, sorted, collect, Predicate

---

## Répartition du travail

| Membre          | Partie |
|-----------------|---|
| Malika          | Gestion opérationnelle (missions + chauffeurs + statistiques) |
| Nirmala / Sofia | Gestion des véhicules |
| Jennifer        | Gestion des incidents et maintenance |