# Mapping API — Front MVP

Le `httpClient` porte le préfixe `http://localhost:8080/api` par défaut. Les services métier ne déclarent donc que la partie située après `/api`.

| Backend Spring | Service Front | Usage MVP |
| --- | --- | --- |
| `GET /api/cafes` | `coffeeService.getAll()` | choix du café (#108) |
| `GET /api/cafes/{id}` | `coffeeService.getById(id)` | détail café |
| `GET /api/machines-a-cafe` | `machineService.getAll()` | choix de la machine (#108) |
| `GET /api/machines-a-cafe/{id}` | `machineService.getById(id)` | détail machine |
| `GET /api/consommations` | `consumptionService.getAll()` | historique/dashboard (#109) |
| `POST /api/consommations` | `consumptionService.create(input)` | nouvelle consommation (#108) |
| `PUT /api/consommations/{id}` | `consumptionService.update(id, input)` | évolution CRUD |
| `DELETE /api/consommations/{id}` | `consumptionService.remove(id)` | évolution CRUD |

## Règle d'architecture

Une View Vue exprime une intention (`charger les machines`, `créer une consommation`) et appelle le service correspondant. Seul le service connaît le segment d'URL métier ; seul `httpClient` connaît l'hôte et le préfixe `/api`. Cela évite de propager un changement de mapping Spring dans les composants.
