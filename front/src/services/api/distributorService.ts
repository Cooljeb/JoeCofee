import { httpClient } from './httpClient'
import type { Distributor, DistributorInput } from '../../types/distributor'

const endpoint = '/distributeurs'

/** Point d'accès unique du Front au domaine Distributeur. */
export const distributorService = {
  getAll: () => httpClient.get<Distributor[]>(endpoint),
  getById: (id: number) => httpClient.get<Distributor>(`${endpoint}/${id}`),
  create: (payload: DistributorInput) => httpClient.post<Distributor>(endpoint, payload),
  update: (id: number, payload: DistributorInput) => httpClient.put<Distributor>(`${endpoint}/${id}`, payload),
  remove: (id: number) => httpClient.delete<void>(`${endpoint}/${id}`)
}
