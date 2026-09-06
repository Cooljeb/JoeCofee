☕ JoeCoffee

Bibliothèque des cafés

Projet personnel de développement visant à répertorier les cafés consommés, les machines utilisées et les réglages associés.

🎯 Objectif

Pratiquer et consolider les bases du développement logiciel à travers un projet concret, de bout en bout (backend, frontend, persistance et déploiement).

🛠️ Stack technique

Backend : Java – API REST

Frontend : à initialiser dans `/front` après validation du CDC et du choix technique

Base de données : SQLite

Déploiement : Raspberry Pi Zero 2 W

🗂️ Organisation du dépôt

- `/back` : application backend complète (Maven, sources Java, ressources, tests et données)
- `/front` : emplacement réservé au futur frontend
- racine : documentation et fichiers communs au projet

Pour travailler sur le backend :

```bash
cd back
./mvnw test
```

Sous Windows :

```powershell
cd back
.\mvnw.cmd test
```

Le déplacement du backend dans `/back` ne modifie pas les packages Java : l'arborescence Maven interne reste `src/main/java` et `src/test/java`, donc les imports Java existants restent valides.

✨ Fonctionnalités prévues

Inclus dans le MVP

Gestion des cafés (CRUD)

Gestion des commerçants (CRUD)

Gestion des machines à café (CRUD)

Enregistrement des consommations

Consultation, modification et suppression des données

API REST documentée via Swagger / OpenAPI

Hors MVP (évolutions envisagées)

Authentification / autorisation (JWT, rôles, sécurité)

Gestion des utilisateurs

Pagination, tri et filtres avancés

Recherche full-text avancée

Gestion des erreurs avancée et internationalisation

Frontend complet et UX avancée

Déploiement automatisé (CI/CD)

🧱 Architecture

Architecture en couches inspirée des bonnes pratiques Spring :

Controller : exposition des endpoints REST

Service : logique métier et règles de gestion

Repository : accès aux données (Spring Data)

DTO / Mapper : séparation entre modèles internes et données exposées

🚧 État du projet

Le backend du MVP couvre les opérations CRUD essentielles et l'API REST. Le projet passe maintenant à la préparation du frontend, en conservant une séparation claire `/back` et `/front`.

🧠 Choix techniques & bonnes pratiques

Utilisation d’une API REST pour une séparation claire frontend / backend

DTO sous forme de records pour des objets immuables et explicites

Séparation stricte des couches (Controller / Service / Repository)

Base de données SQLite pour la simplicité et la légèreté

Déploiement sur Raspberry Pi pour valider un cycle complet de mise en production

Ces choix sont orientés apprentissage, lisibilité et maintenabilité du code.
