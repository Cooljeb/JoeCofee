import { apiRequest } from './httpClient'
import type { Machine, MachineInput } from '../../types/machine'

/**
 * Service d'accès aux machines à café.
 * `httpClient` contient déjà le préfixe `/api` : ce service reprend donc
 * uniquement le mapping métier réel de `MachineACafeController`.
 */
const RESOURCE = '/machines-a-cafe'

export const machineService = {
  getAll: () => apiRequest<Machine[]>(RESOURCE),
  getById: (id: number) => apiRequest<Machine>(`${RESOURCE}/${id}`),
  create: (input: MachineInput) => apiRequest<Machine>(RESOURCE, {
    method: 'POST',
    body: JSON.stringify(input)
  }),
  update: (id: number, input: MachineInput) => apiRequest<Machine>(`${RESOURCE}/${id}`, {
    method: 'PUT',
    body: JSON.stringify(input)
  }),
  remove: (id: number) => apiRequest<void>(`${RESOURCE}/${id}`, { method: 'DELETE' })
}
