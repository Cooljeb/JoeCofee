import { httpClient } from './httpClient'
import type { Roaster, RoasterInput } from '../../types/roaster'

// L'endpoint conserve volontairement le nom exact exposé par le Controller Spring.
const endpoint = '/artisanTorrefacteur'

export const roasterService = {
  getAll: () => httpClient.get<Roaster[]>(endpoint),
  getById: (id: number) => httpClient.get<Roaster>(`${endpoint}/${id}`),
  create: (payload: RoasterInput) => httpClient.post<Roaster>(endpoint, payload),
  update: (id: number, payload: RoasterInput) => httpClient.put<Roaster>(`${endpoint}/${id}`, payload),
  remove: (id: number) => httpClient.delete<void>(`${endpoint}/${id}`)
}
