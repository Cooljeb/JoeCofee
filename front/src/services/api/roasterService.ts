import { apiRequest } from './httpClient'
import type { Roaster, RoasterInput } from '../../types/roaster'

// L'endpoint conserve volontairement le nom exact exposé par le Controller Spring.
const endpoint = '/artisanTorrefacteur'

export const roasterService = {
  getAll: () => apiRequest<Roaster[]>(endpoint),
  getById: (id: number) => apiRequest<Roaster>(`${endpoint}/${id}`),
  create: (payload: RoasterInput) => apiRequest<Roaster>(endpoint, { method: 'POST', body: JSON.stringify(payload) }),
  update: (id: number, payload: RoasterInput) => apiRequest<Roaster>(`${endpoint}/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  remove: (id: number) => apiRequest<void>(`${endpoint}/${id}`, { method: 'DELETE' })
}
