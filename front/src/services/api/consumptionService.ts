import { apiRequest } from './httpClient'
import type { Consumption, ConsumptionInput } from '../../types/consumption'

/**
 * Service métier Front des consommations.
 * Il est le seul endroit à connaître `/consommations` : une évolution d'URL
 * backend ne doit jamais imposer de modifier plusieurs composants Vue.
 */
export const consumptionService = {
  getAll: () => apiRequest<Consumption[]>('/consommations'),
  getById: (id: number) => apiRequest<Consumption>(`/consommations/${id}`),
  create: (input: ConsumptionInput) => apiRequest<Consumption>('/consommations', {
    method: 'POST', body: JSON.stringify(input)
  }),
  update: (id: number, input: ConsumptionInput) => apiRequest<Consumption>(`/consommations/${id}`, {
    method: 'PUT', body: JSON.stringify(input)
  }),
  remove: (id: number) => apiRequest<void>(`/consommations/${id}`, { method: 'DELETE' })
}
