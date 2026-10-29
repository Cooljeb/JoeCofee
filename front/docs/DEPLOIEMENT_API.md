# Déploiement JoeCoffee — routage API et CORS (#126)

## Décision recommandée : une seule origine

Publier les fichiers statiques construits par Vite et l'API Spring derrière **le même nom de domaine HTTPS**. Le navigateur appelle `/api/...` ; le reverse proxy transmet ces requêtes à Spring sur le port 8080. Il sert toutes les autres routes Vue via `index.html`.

Cette architecture évite de configurer CORS sur le backend pour le MVP. Le proxy de `vite.config.ts` remplit ce rôle **uniquement en développement** ; il n'existe pas dans les fichiers `dist/`.

> Dépendance : la PR #125 définit `/api` comme URL par défaut du Front. Cette documentation peut être relue séparément mais le déploiement doit utiliser la version du Front contenant ce changement.

## Exemple Nginx (à adapter, pas un déploiement réalisé)

```nginx
server {
    listen 80;
    server_name coffee.example.invalid; # Remplacer et activer HTTPS avant exposition publique.

    root /srv/joecoffee/front/dist;
    index index.html;

    location /api/ {
        # Sans slash final après :8080, le préfixe /api/ est conservé.
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    location / {
        # Les routes Vue (/cafes, /machines, etc.) doivent charger index.html.
        try_files $uri $uri/ /index.html;
    }
}
```

En production, configurer TLS/HTTPS, pare-feu et nom de domaine réels ; ne pas exposer le port 8080 publiquement. Si Spring écoute sur une autre interface, adapter la cible du proxy. Les chemins de fichiers dépendent de l'hôte (par exemple Raspberry Pi) et ne sont pas des valeurs universelles.

Pour construire le Front :

```bash
cd front
npm install
npm run build
```

Déployer ensuite le contenu de `front/dist/` dans le répertoire configuré dans Nginx. **Ne pas définir** `VITE_API_BASE_URL` sur une autre origine : sa valeur est intégrée au build, pas lue dynamiquement au lancement.

## Si deux origines sont réellement nécessaires

Par exemple Front `https://coffee.example.invalid` et API `https://api.example.invalid` : déclarer explicitement cette origine côté Spring et n'autoriser que les méthodes/en-têtes requis. Un POST JSON inter-origines déclenche généralement une requête `OPTIONS` préalable ; vérifier la réponse de prévol dans le navigateur.

Ne pas activer `Access-Control-Allow-Origin: *` par défaut, et ne jamais combiner `*` avec des identifiants de session. La liste des origines autorisées doit dépendre de l'environnement de déploiement, non être codée en dur dans une View Vue.

**Aucun changement CORS backend n'est nécessaire** pour la solution même origine. Si une séparation devient obligatoire, ouvrir une sous-tâche backend explicite avec l'URL de production connue.

## Recette de déploiement (à effectuer sur l'hôte cible)

- [ ] Vérifier que `/api/consommations` répond avec du JSON via le domaine public (et non `index.html`).
- [ ] Ouvrir directement `/consommations`, `/cafes` et `/machines`, puis recharger : aucune 404.
- [ ] Depuis le navigateur, créer une consommation de test : POST vers `/api/consommations`, sans erreur CORS.
- [ ] Vérifier que l'API n'est pas exposée directement sur le port 8080 depuis Internet.
- [ ] Vérifier HTTPS, le certificat et les en-têtes proxy.
- [ ] Relever la version/commit du Front et du Back testés ; ne pas annoncer de validation sans exécution réelle.

Cette documentation formalise une **stratégie**, pas une preuve de fonctionnement en production.

— Joe IA
