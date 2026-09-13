# JoeCoffee — Cartographie API et socle Front

> Issue #100 — document de préparation à l'implémentation Front.

## 1. Décision technique Front

Le socle retenu pour le MVP est **Vue 3 + TypeScript + Vite**.

Raisons :
- application personnelle de taille modérée ;
- besoin d'un frontend responsive simple à maintenir ;
- courbe d'apprentissage plus légère qu'Angular pour ce périmètre ;
- composants réutilisables et Composition API adaptés aux écrans du CDC ;
- TypeScript conserve un typage explicite des contrats avec le backend Java.

Socle minimal proposé :
- Vue 3 + TypeScript ;
- Vite pour build/dev ;
- Vue Router pour les écrans ;
- CSS responsive mobile-first, sans framework UI imposé au MVP ;
- appels HTTP regroupés dans une couche `services/api` ;
- types TypeScript séparés des composants ;
- Vitest pour les tests unitaires du Front.

**Pinia n'est pas imposé au démarrage.** On l'ajoutera uniquement si un état global partagé devient réellement nécessaire. Pour le MVP, les données peuvent rester gérées par vues/composables afin d'éviter de sur-architecturer.

## 2. Architecture cible

```text
front/
├── src/
│   ├── components/       # composants UI réutilisables
│   ├── views/            # écrans routés
│   ├── router/           # routes
│   ├── services/api/     # accès REST backend
│   ├── types/            # contrats TypeScript
│   ├── composables/      # logique réutilisable
│   ├── assets/
│   └── styles/
├── tests/
└── ...
```

Règle : un composant UI ne construit pas directement ses URL REST. Les appels passent par les services API.

## 3. API réellement exposée par le backend

Le backend utilise le préfixe `/api` et expose actuellement six ressources REST.

### Consommations — `/api/consommations`

- `GET /api/consommations` : liste
- `GET /api/consommations/{id}` : détail
- `POST /api/consommations` : création
- `PUT /api/consommations/{id}` : modification
- `DELETE /api/consommations/{id}` : suppression

Entrée actuellement requise pour création/modification :

```text
reglageBroyeur: Byte
reglageIntensite: Byte
cafeId: Integer
machineACafeId: Integer
```

C'est le flux prioritaire du MVP : choix du café + machine + réglages -> POST consommation -> confirmation -> rafraîchissement de l'historique.

### Cafés — `/api/cafes`

CRUD standard :
- `GET /api/cafes`
- `GET /api/cafes/{id}`
- `POST /api/cafes`
- `PUT /api/cafes/{id}`
- `DELETE /api/cafes/{id}`

Endpoints complémentaires :
- `GET /api/cafes/filter?type=...&label=...`
- `GET /api/cafes/by-commercant/{commercantId}`

### Machines — `/api/machines-a-cafe`

CRUD standard :
- `GET /api/machines-a-cafe`
- `GET /api/machines-a-cafe/{id}`
- `POST /api/machines-a-cafe`
- `PUT /api/machines-a-cafe/{id}`
- `DELETE /api/machines-a-cafe/{id}`

Recherche complémentaire :
- `GET /api/machines-a-cafe/name/{name}`

### Marques — `/api/marques`

CRUD standard + :
- `GET /api/marques/name/{name}`

### Distributeurs — `/api/distributeurs`

CRUD standard + :
- `GET /api/distributeurs/name/{name}`
- `GET /api/distributeurs/name-distrib/{name}`

### Artisans torréfacteurs — `/api/artisanTorrefacteur`

CRUD standard + :
- `GET /api/artisanTorrefacteur/name/{name}`

> Le chemin `artisanTorrefacteur` est conservé tel quel côté Front pour le moment : cette documentation décrit l'API existante et ne renomme pas les contrats du backend.

## 4. Flux MVP Front -> Back

### Dashboard / accueil

Au chargement :
1. `GET /api/consommations`
2. affichage des consommations récentes ;
3. récupération des cafés/machines seulement si nécessaires aux libellés ou aux actions de l'écran.

Le MVP ne doit pas multiplier les appels uniquement pour produire des statistiques non prévues par le backend.

### Nouvelle consommation

1. `GET /api/cafes`
2. `GET /api/machines-a-cafe`
3. l'utilisateur choisit café et machine ;
4. il renseigne broyeur/intensité ;
5. `POST /api/consommations` ;
6. succès : confirmation courte puis retour historique/dashboard ;
7. échec : message contextualisé sans perdre les valeurs saisies.

### Historique

- `GET /api/consommations`
- consultation d'un élément possible via `GET /api/consommations/{id}` ;
- modification/suppression restent disponibles mais secondaires dans le parcours MVP.

### Administration simple des référentiels

Les vues Cafés et Machines utilisent leur CRUD respectif. Marques, distributeurs et artisans restent des référentiels secondaires et ne doivent pas saturer la navigation principale.

## 5. Composants communs à prévoir

- `AppShell` : structure générale ;
- `DesktopSidebar` / `MobileBottomNav` : navigation adaptée au support ;
- `PageHeader` ;
- `PrimaryActionButton` ;
- `CoffeeCard` ;
- `MachineCard` ;
- `ConsumptionCard` ;
- champs/selects de formulaire communs ;
- `ConfirmDialog` ;
- `Toast` ;
- `LoadingSkeleton` ;
- `EmptyState` ;
- `ErrorState`.

Mobile et desktop partagent les données et règles métier, pas forcément la même mise en page.

## 6. Responsive / UX

### Mobile

- navigation principale en bas ;
- action `+ consommation` accessible rapidement ;
- formulaires en une colonne ;
- cartes pleine largeur ;
- cibles tactiles confortables ;
- pas de tableau large nécessitant un scroll horizontal pour les usages principaux.

### Desktop

- sidebar ou navigation latérale ;
- contenu plus dense ;
- listes et formulaires capables d'utiliser l'espace horizontal ;
- accès simultané à l'historique et aux actions principales lorsque pertinent.

### Animations

Animations courtes et fonctionnelles uniquement :
- transition écran/étape ;
- feedback bouton ;
- apparition toast ;
- skeleton de chargement ;
- confirmation visuelle après création d'une consommation.

Pas d'animation permanente ou bloquante.

## 7. Gestion des erreurs et états HTTP

Le Front doit traiter au minimum :
- `200` : lecture/mise à jour réussie ;
- `201` : création réussie ;
- `204` : suppression réussie ;
- `400` : données invalides ;
- `404` : ressource absente ;
- `409` : conflit métier lorsque l'API l'expose ;
- erreur réseau / backend indisponible.

La couche API normalise les erreurs avant affichage afin d'éviter de disperser la logique HTTP dans les composants.

## 8. Points d'attention relevés avant développement Front

1. **CORS** : aucune configuration CORS dédiée n'a été trouvée dans le code inspecté. À valider lors du premier branchement Vue <-> Spring si les deux applications tournent sur des origines différentes.
2. **Pagination** : les endpoints de listes renvoient actuellement des listes complètes. Suffisant pour le MVP ; pas de pagination Front fictive à introduire.
3. **Convention d'URL** : `artisanTorrefacteur` diffère du style kebab/pluriel des autres ressources. Le Front doit encapsuler cette particularité dans son service API.
4. **Erreurs** : les contrôleurs documentent 400/404/409 selon les ressources. Le Front doit rester tolérant à un format de message d'erreur qui pourra être harmonisé plus tard.
5. **Consommation** : le DTO d'entrée exige actuellement les quatre champs, donc le formulaire ne doit pas autoriser un envoi incomplet.

## 9. Ordre d'implémentation proposé après validation de cette PR

1. initialiser Vue 3 + TypeScript + Vite et l'ossature responsive ;
2. mettre en place router + couche API + types ;
3. développer le parcours `Nouvelle consommation` ;
4. développer historique/dashboard ;
5. développer Cafés et Machines ;
6. ajouter les référentiels secondaires ;
7. consolider tests, erreurs, responsive et micro-animations.

Chaque lot doit devenir une issue GitHub dédiée avant implémentation.

— Joe IA
