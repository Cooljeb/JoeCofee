import { apiRequest } from './httpClient'
import type { Coffee } from '../../types/coffee'

/** Couche REST dédiée aux cafés : les Views ne connaissent jamais `/cafes`. */
export const coffeeService = {
  getAll: () => apiRequest<Coffee[]>('/cafes'),
  getById: (id: number) => apiRequest<Coffee>(`/cafes/${id}`)
}
