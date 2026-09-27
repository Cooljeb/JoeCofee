# Frontend JoeCoffee

Frontend MVP de JoeCoffee en **Vue 3 + TypeScript + Vite**.

## Prérequis

- Node.js LTS récent ;
- npm ;
- backend JoeCoffee démarré pour les parcours utilisant l'API.

## Démarrage

Depuis le dossier `front` :

```bash
npm install
npm run dev
```

Vite affiche l'URL locale à ouvrir dans le navigateur.

## Vérifications avant PR

```bash
npm run build
npm test
```

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
- `docs/` : décisions et documents Front complémentaires.

## Backend

Le backend est traité comme un contrat : avant d'ajouter ou modifier un type Front, vérifier le Controller Spring et ses DTO IN/OUT. Une divergence découverte doit être documentée plutôt que compensée par un champ inventé côté Vue.
