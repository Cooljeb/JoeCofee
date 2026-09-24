import { apiRequest } from './httpClient'
import type { Coffee, CoffeeInput } from '../../types/coffee'

/**
 * Couche REST dédiée aux cafés : les Views ne connaissent jamais `/cafes`.
 * Les verbes et chemins ci-dessous reflètent directement `CafeController`.
 */
const RESOURCE = '/cafes'

export const coffeeService = {
  getAll: () => apiRequest<Coffee[]>(RESOURCE),
  getById: (id: number) => apiRequest<Coffee>(`${RESOURCE}/${id}`),
  create: (input: CoffeeInput) => apiRequest<Coffee>(RESOURCE, {
    method: 'POST',
    body: JSON.stringify(input)
  }),
  update: (id: number, input: CoffeeInput) => apiRequest<Coffee>(`${RESOURCE}/${id}`, {
    method: 'PUT',
    body: JSON.stringify(input)
  }),
  remove: (id: number) => apiRequest<void>(`${RESOURCE}/${id}`, { method: 'DELETE' })
}
