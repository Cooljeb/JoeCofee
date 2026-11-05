import { apiRequest } from './httpClient'
import type { Brand, BrandInput } from '../../types/brand'

/**
 * Frontière métier pour /api/marques.
 * Les Views manipulent des marques sans connaître l'URL REST ni fetch.
 */
const endpoint = '/marques'

export const brandService = {
  getAll: () => apiRequest<Brand[]>(endpoint),
  getById: (id: number) => apiRequest<Brand>(`${endpoint}/${id}`),
  getByName: (name: string) => apiRequest<Brand>(`${endpoint}/name/${encodeURIComponent(name)}`),
  create: (payload: BrandInput) => apiRequest<Brand>(endpoint, { method: 'POST', body: JSON.stringify(payload) }),
  update: (id: number, payload: BrandInput) => apiRequest<Brand>(`${endpoint}/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  remove: (id: number) => apiRequest<void>(`${endpoint}/${id}`, { method: 'DELETE' })
}
