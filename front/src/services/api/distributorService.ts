import { apiRequest } from './httpClient'
import type { Distributor, DistributorInput } from '../../types/distributor'

const endpoint = '/distributeurs'

/** Point d'accès unique du Front au domaine Distributeur. */
export const distributorService = {
  getAll: () => apiRequest<Distributor[]>(endpoint),
  getById: (id: number) => apiRequest<Distributor>(`${endpoint}/${id}`),
  create: (payload: DistributorInput) => apiRequest<Distributor>(endpoint, { method: 'POST', body: JSON.stringify(payload) }),
  update: (id: number, payload: DistributorInput) => apiRequest<Distributor>(`${endpoint}/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  remove: (id: number) => apiRequest<void>(`${endpoint}/${id}`, { method: 'DELETE' })
}
