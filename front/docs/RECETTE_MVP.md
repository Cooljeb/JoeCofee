# Recette manuelle — Front MVP (#111)

Cette recette complète Vitest et le build : elle vérifie **le vrai navigateur**, le proxy Vite et le backend Spring. Ne pas déclarer le MVP validé tant que les cases ne sont pas cochées.

## Préparer l'environnement

Prérequis : Java 21, Node.js 22 et npm, deux terminaux. Les commandes partent de la racine du dépôt. Le backend utilise SQLite (`back/data/joeCoffee.db`) : **sauvegarder la base avant les essais** et utiliser des données de test.

Terminal A (Spring) :

```bash
cd back
./mvnw spring-boot:run
```

Sous Windows, utiliser `mvnw.cmd spring-boot:run` dans `back`. Vérifier dans les logs que Spring écoute sur le port **8080**. Si le démarrage échoue, relever l'erreur avant de poursuivre.

Terminal B (Vue) :

```bash
cd front
npm install
npm test
npm run build
npm run dev
```

Ouvrir l'URL indiquée par Vite (habituellement `http://localhost:5173`). En développement, le navigateur appelle `/api` sur **la même origine** et Vite transmet vers Spring `:8080`. Dans l'onglet Réseau du navigateur, vérifier que les requêtes `/api/...` reçoivent une réponse HTTP et non le HTML de l'application. Aucun endpoint ne doit être construit dans une View.

## Parcours fonctionnel (noter OK / KO et la capture en cas d'échec)

- [ ] Accueil : chargement, derniers cafés ou état vide ; bouton Réessayer si API indisponible.
- [ ] Cafés : afficher la liste ; créer/modifier un café de test avec un **identifiant de commerçant existant** ; vérifier l'affichage après actualisation.
- [ ] Machines : afficher la liste et les marques ; créer/modifier une machine de test ; vérifier le rafraîchissement.
- [ ] Nouvelle consommation : choisir café + machine existants ; saisir broyeur/intensité de 1 à 127 ; enregistrer **une seule fois**.
- [ ] Après l'enregistrement : confirmation visible puis navigation vers `/consommations` ; nouvelle consommation présente dans l'historique.
- [ ] Validation : valeurs vides, décimales, zéro et 128 refusées ; aucune requête POST déclenchée pour une saisie invalide.
- [ ] API coupée temporairement : message d'erreur compréhensible et nouvelle tentative opérationnelle après redémarrage.
- [ ] Actualisation directe de `/consommations`, `/cafes` et `/machines` : pas de 404 côté Vite.

## Accessibilité et responsive

- [ ] Clavier : Tab depuis le haut affiche « Aller au contenu principal » ; Entrée place l'utilisateur sur le contenu principal.
- [ ] Focus visible sur liens, boutons et champs ; tous les contrôles accessibles sans souris.
- [ ] Les libellés des champs et les messages d'erreur sont compréhensibles ; pas de blocage silencieux.
- [ ] Écran 375 px (mobile), 768 px (tablette) et 1280 px (desktop) : aucune coupure de contenu ni barre de navigation superposée au formulaire.
- [ ] Tester l'option système « Réduire les animations » : les confirmations restent lisibles sans animation.
- [ ] Vérifier les contrastes sur appareil réel ; les tests unitaires ne mesurent pas la perception visuelle.

## Rapport de recette

Consigner la date, l'OS, le navigateur, la branche/commit testés, les commandes réellement réussies et les cases KO. Joindre les erreurs **sans mots de passe, jetons ni données personnelles**. En cas de bug, créer une issue dédiée ou rattacher la correction à #111 si elle relève bien de la stabilisation.

La PR #125 reste en brouillon tant que la recette navigateur n'est pas confirmée. Ne jamais merger automatiquement vers `Develop`.
