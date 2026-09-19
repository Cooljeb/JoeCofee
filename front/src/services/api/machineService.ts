import { apiRequest } from './httpClient'
import type { Machine } from '../../types/machine'

/**
 * Service d'accès aux machines à café.
 * La View demande des machines ; ce service est responsable de traduire cette
 * intention métier en appel REST vers Spring.
 */
export const machineService = {
  getAll: () => apiRequest<Machine[]>('/machines'),
  getById: (id: number) => apiRequest<Machine>(`/machines/${id}`)
}
