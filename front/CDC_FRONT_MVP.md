# JoeCoffee — CDC Front MVP

## 1. Objectif
Créer une interface simple, responsive et agréable permettant d'utiliser le backend JoeCoffee existant sans exposer sa complexité technique.

Le MVP doit répondre d'abord à un besoin concret : **enregistrer une consommation de café rapidement et retrouver les données utiles autour des cafés et machines**.

Le frontend ne doit pas encore ajouter de logique métier inexistante côté backend.

## 2. Parties prenantes et rôles
- **Utilisateur** : consulte, crée, modifie et supprime les données utiles à son usage personnel.
- **Frontend JoeCoffee** : présente les données, guide la saisie, gère navigation, validation visuelle et retours utilisateur.
- **API Backend JoeCoffee** : porte les règles métier, la validation serveur et la persistance.

Pour le MVP, aucun système multi-utilisateur ou gestion fine de droits n'est prévu.

## 3. Périmètre MVP V1
### Fonction principale
- créer une consommation ;
- consulter l'historique des consommations ;
- modifier ou supprimer une consommation si l'API le permet ;
- consulter et administrer les cafés ;
- consulter et administrer les machines à café.

### Référentiels secondaires
Les marques, distributeurs et artisans torréfacteurs restent accessibles via des écrans secondaires ou depuis les formulaires concernés. Ils ne doivent pas surcharger la navigation principale.

## 4. Navigation
### Mobile
Navigation basse avec 4 entrées maximum :
1. Accueil
2. Consommations
3. Cafés
4. Machines

Un bouton d'action `+` clairement visible permet de créer rapidement une consommation.

### PC / tablette large
- navigation latérale ou supérieure persistante ;
- zone de contenu plus large ;
- accès direct aux listes et actions d'administration ;
- possibilité d'afficher davantage d'informations sans multiplier les écrans.

Le responsive doit adapter l'expérience et non simplement réduire la version PC.

## 5. Écrans MVP
### Accueil
- résumé simple de l'activité ;
- dernières consommations ;
- raccourci vers `Nouvelle consommation` ;
- accès rapide aux cafés et machines.

### Nouvelle consommation
Parcours court et guidé :
1. choisir un café ;
2. choisir une machine ;
3. saisir les informations demandées par le modèle backend ;
4. confirmer ;
5. afficher un retour de succès et revenir vers l'historique ou l'accueil.

Objectif UX : limiter le nombre d'actions nécessaires et éviter les formulaires longs lorsque plusieurs étapes simples sont plus lisibles sur mobile.

### Historique des consommations
- liste chronologique ;
- détail d'une consommation ;
- modification ;
- suppression avec confirmation.

### Cafés
- liste ;
- détail ;
- création ;
- modification ;
- suppression avec confirmation.

### Machines
- liste ;
- détail ;
- création ;
- modification ;
- suppression avec confirmation.

### Référentiels secondaires
Marques, distributeurs et artisans torréfacteurs : écrans CRUD simples, accessibles depuis une section secondaire ou les formulaires qui les utilisent.

## 6. Composants communs
- `AppNavigation`
- `PageHeader`
- `PrimaryActionButton`
- `CoffeeCard`
- `MachineCard`
- `ConsumptionCard`
- `FormField`
- `SelectField`
- `ConfirmDialog`
- `Toast`
- `LoadingSkeleton`
- `EmptyState`
- `ErrorState`

Les composants doivent être réutilisables et ne pas embarquer de logique métier spécifique inutile.

## 7. États UX obligatoires
Chaque écran consommant l'API doit prévoir :
- chargement ;
- données disponibles ;
- aucune donnée ;
- erreur serveur/réseau ;
- action réussie ;
- validation de formulaire incorrecte.

Les erreurs techniques doivent être traduites en messages compréhensibles pour l'utilisateur.

## 8. Animations et micro-interactions
Animations courtes et utiles uniquement :
- transition légère entre écrans ou étapes ;
- feedback visuel sur bouton pressé ;
- apparition douce des cartes ;
- toast après succès ou erreur ;
- animation courte de validation après création d'une consommation.

Cible indicative : 150 à 300 ms pour les micro-interactions.

Pas d'animation permanente ou décorative gênant la lecture.

## 9. Direction UI
Les trois propositions visuelles déjà validées servent de références.

Pour le MVP, synthèse recommandée :
- structure claire et dashboard du design moderne/chaleureux ;
- lisibilité et sobriété du design minimaliste ;
- éléments immersifs du design fun uniquement pour les actions clés, notamment la validation d'une consommation.

Le visuel doit rester chaleureux, simple et orienté café sans sacrifier la lisibilité.

## 10. Front ↔ Back
Le frontend consommera l'API REST existante.

Les ressources backend identifiées à ce stade sont :
- cafés ;
- consommations ;
- machines à café ;
- marques ;
- distributeurs ;
- artisans torréfacteurs.

Principes :
- le frontend ne duplique pas les règles métier du backend ;
- les identifiants techniques sont masqués lorsqu'ils n'apportent rien à l'utilisateur ;
- les listes nécessaires à un formulaire sont chargées avant saisie ou à la demande ;
- les erreurs HTTP sont transformées en retours UX clairs ;
- la définition précise des DTO, endpoints et champs sera vérifiée dans une issue technique dédiée avant implémentation.

## 11. Responsive
### Mobile
- priorité au pouce et à l'action principale ;
- une colonne ;
- formulaires découpés si nécessaire ;
- navigation basse ;
- actions destructives moins accessibles accidentellement.

### PC
- exploitation de l'espace horizontal ;
- navigation persistante ;
- listes plus denses ;
- formulaires pouvant être affichés en panneau ou modale lorsque pertinent.

## 12. Accessibilité minimale
- contraste lisible ;
- libellés explicites ;
- focus clavier visible ;
- zones tactiles suffisamment grandes ;
- ne pas transmettre une information uniquement par la couleur ;
- messages d'erreur associés au champ concerné.

## 13. Hors périmètre MVP
À réserver pour V1.1/V2 selon intérêt :
- statistiques avancées ;
- favoris ;
- recommandations ;
- profils utilisateurs ;
- authentification multi-utilisateur ;
- gamification ;
- notifications ;
- fonctionnement hors ligne ;
- PWA complète.

## 14. Critères d'acceptation du MVP Front
Le MVP sera considéré cohérent si :
- une consommation peut être créée simplement sur mobile et PC ;
- les consommations sont consultables ;
- cafés et machines sont administrables ;
- les référentiels secondaires sont accessibles sans surcharger la navigation ;
- chaque écran gère chargement, vide, erreur et succès ;
- le comportement mobile est réellement adapté ;
- aucun besoin fonctionnel ne repose sur une donnée absente de l'API sans issue backend explicite.

## 15. Suite recommandée
Après validation de ce CDC :
1. cartographier précisément les endpoints et DTO du backend ;
2. choisir la technologie frontend ;
3. découper le MVP en issues indépendantes ;
4. initialiser le projet dans `/front` ;
5. implémenter d'abord le shell responsive et les composants communs ;
6. brancher ensuite les parcours fonctionnels à l'API.

— Joe IA
