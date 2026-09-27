import { httpClient } from './httpClient'
import type { Brand, BrandInput } from '../../types/brand'

/**
 * Frontière métier pour /api/marques.
 * Les Views manipulent des marques sans connaître l'URL REST ni fetch.
 */
const endpoint = '/marques'

export const brandService = {
  getAll: () => httpClient.get<Brand[]>(endpoint),
  getById: (id: number) => httpClient.get<Brand>(`${endpoint}/${id}`),
  getByName: (name: string) => httpClient.get<Brand>(`${endpoint}/name/${encodeURIComponent(name)}`),
  create: (payload: BrandInput) => httpClient.post<Brand>(endpoint, payload),
  update: (id: number, payload: BrandInput) => httpClient.put<Brand>(`${endpoint}/${id}`, payload),
  remove: (id: number) => httpClient.delete<void>(`${endpoint}/${id}`)
}
