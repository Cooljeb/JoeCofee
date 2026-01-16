☕ JoeCoffee

Bibliothèque des cafés

Projet personnel de développement visant à répertorier les cafés consommés, les machines utilisées et les réglages associés.

🎯 Objectif

Pratiquer et consolider les bases du développement logiciel à travers un projet concret, de bout en bout (backend, frontend, persistance et déploiement).

🛠️ Stack technique

Backend : Java – API REST

Frontend : Angular

Base de données : SQLite

Déploiement : Raspberry Pi Zero 2 W


✨ Fonctionnalités prévues

Inclus dans le MVP

Gestion des cafés (CRUD)

Gestion des commerçants (CRUD)

Gestion des machines à café (CRUD)

Enregistrement des consommations

Consultation, modification et suppression des données

API REST documentée via Swagger / OpenAPI


Hors MVP (évolutions envisagées)

Les fonctionnalités suivantes sont volontairement exclues du MVP afin de rester focalisé sur les fondamentaux :

Authentification / autorisation (JWT, rôles, sécurité)

Gestion des utilisateurs

Pagination, tri et filtres avancés

Recherche full-text avancée

Gestion des erreurs avancée et internationalisation

Tests automatisés (unitaires / intégration)

Frontend complet et UX avancée

Déploiement automatisé (CI/CD)


Ces éléments pourront être intégrés dans des itérations ultérieures.

🧱 Architecture

Architecture en couches inspirée des bonnes pratiques Spring :

Controller : exposition des endpoints REST

Service : logique métier et règles de gestion

Repository : accès aux données (Spring Data)

DTO / Mapper : séparation entre modèles internes et données exposées


🚧 État du projet

Projet en cours de développement, à usage personnel.

MVP – Avancement actuel

Le projet est volontairement construit de manière progressive.

Ordre de réalisation actuel du MVP :

1. Implémentation des Repositories


2. Mise en place des Services (logique métier)


3. Création des DTO (records) et Mappers


4. Exposition des fonctionnalités via les Controllers REST


5. Mise en place de la documentation de l’API avec Swagger / OpenAPI



L’objectif du MVP est de disposer d’une API REST fonctionnelle, claire et documentée, couvrant les opérations CRUD essentielles.

🧠 Choix techniques & bonnes pratiques

Utilisation d’une API REST pour une séparation claire frontend / backend

DTO sous forme de records pour des objets immuables et explicites

Séparation stricte des couches (Controller / Service / Repository)

Base de données SQLite pour la simplicité et la légèreté

Déploiement sur Raspberry Pi pour valider un cycle complet de mise en production


Ces choix sont orientés apprentissage, lisibilité et maintenabilité du code.