# Frontend JoeCoffee

Frontend MVP de JoeCoffee en **Vue 3 + TypeScript + Vite**.

## Prérequis

- Node.js 22.22.2+ (branche 22) ou 24.15+ (branche 24), conformément aux moteurs des dépendances ;
- npm ;
- backend JoeCoffee démarré pour les parcours utilisant l'API.

## Démarrage

Depuis le dossier `front` :

```bash
npm ci
npm run dev
```

Vite affiche l'URL locale à ouvrir dans le navigateur.

## Vérifications avant PR

```bash
npm ci
npm test
npm run build
```

La PR vers `Develop` déclenche également `.github/workflows/front-mvp.yml` sur GitHub Actions (Node 22). Le workflow installe les dépendances, exécute Vitest puis vérifie TypeScript et le build Vite. Il faut consulter le statut du run avant toute validation.

`package-lock.json` est versionné : `npm ci` installe les versions exactes validées en CI. Les versions de `package.json` sont figées pour éviter une mise à jour majeure implicite lors d'une installation. TypeScript reste dans la série 5.9 pour éviter l'incompatibilité avec `vue-tsc` rencontrée auparavant.

`npm run build` exécute d'abord `vue-tsc` puis le build Vite. `npm test` lance Vitest en mode non interactif.

> Ne jamais annoncer ces commandes comme vertes sans les avoir réellement exécutées dans un environnement Node.

## Architecture à connaître quand on débute avec Vue

Le flux API suit volontairement une seule direction :

`View / Component -> service métier -> httpClient -> API Spring`

- `src/views` orchestre l'état d'écran et les interactions utilisateur ;
- `src/components` contient les éléments réutilisables ;
- `src/services/api/*Service.ts` connaît les endpoints d'un domaine ;
- `src/services/api/httpClient.ts` centralise l'URL de base, le transport HTTP, les erreurs et les logs de frontière ;
- `src/types` reflète les DTO Spring réellement exposés.

Une View ne doit donc **jamais construire une URL REST**. Si un écran a besoin d'une nouvelle ressource, on complète d'abord son type et son service.

## Parcours MVP

Le MVP couvre notamment :

- nouvelle consommation ;
- historique et dashboard ;
- consultation/gestion des cafés ;
- consultation/gestion des machines ;
- navigation responsive téléphone / tablette / desktop.

## Documentation complémentaire

- `CDC_FRONT_MVP.md` : périmètre fonctionnel et UX ;
- `ARCHITECTURE_FRONT_API.md` : conventions détaillées de la couche API ;
- `docs/RECETTE_MVP.md` : procédure reproductible de recette navigateur avec Spring, clavier et responsive ;
- `docs/` : décisions et documents Front complémentaires.

## Backend

Le backend est traité comme un contrat : avant d'ajouter ou modifier un type Front, vérifier le Controller Spring et ses DTO IN/OUT. Une divergence découverte doit être documentée plutôt que compensée par un champ inventé côté Vue.

## Contrats des catalogues

Le formulaire Cafés reprend les enums Spring `TypeCafe` (ARABICA, ROBUSTA, BLEND) et `LabelCafe`, les longueurs obligatoires (nom 3–50, description 5–255) et un identifiant commerçant entier. Le formulaire Machines impose nom, référence et description non vides, plus une marque issue du référentiel. Ces contrôles client évitent des requêtes invalides ; Spring conserve la validation de référence.

## Stabilisation MVP

Avant validation du MVP, vérifier sur téléphone, tablette et desktop : navigation clavier, focus visible, libellés des formulaires, états chargement/vide/erreur/succès et comportement avec `prefers-reduced-motion`.

Les tests automatisés couvrent en priorité la frontière HTTP et les composants réutilisés dans les parcours critiques. Ils complètent, mais ne remplacent pas, la vérification du parcours « nouvelle consommation -> historique » avec le backend démarré.

## Socle Vite et design

`index.html` monte `src/main.ts`, qui instancie `App.vue` et `AppShell.vue`.
`src/styles/main.css` centralise la palette café/crème, la navigation mobile,
les contrastes de boutons, les focus clavier et la réduction des animations.
Le Front appelle par défaut **`/api` sur sa propre origine**. En développement,
`vite.config.ts` relaie ce chemin vers `http://localhost:8080` (Spring), ce qui
évite les erreurs CORS entre le navigateur et le backend local.

En production, configurer un **reverse proxy** `/api` vers Spring sur la même
origine que le Front. La variable optionnelle `VITE_API_BASE_URL` permet de
cibler une API différente ; si elle désigne une autre origine, le serveur API
doit autoriser explicitement les origines CORS correspondantes. La décision
de déploiement est suivie dans l'issue #126, séparément du MVP local.

Les contrôles GitHub Actions exécutent `npm ci`, `npm test` puis `npm run build`.
Leur résultat doit être vérifié sur le **dernier commit** avant validation :
un succès sur un commit antérieur ne garantit pas le nouveau lot.
La recette avec Spring et la revue visuelle restent à effectuer selon
`docs/RECETTE_MVP.md`.
