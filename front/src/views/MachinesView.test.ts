// @vitest-environment jsdom
import { beforeEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import MachinesView from './MachinesView.vue'
import { machineService } from '../services/api/machineService'
import { brandService } from '../services/api/brandService'
import type { Machine } from '../types/machine'

// Les référentiels et mutations sont simulés : le contrat HTTP reste au service.
vi.mock('../services/api/machineService', () => ({
  machineService: { getAll: vi.fn(), create: vi.fn(), update: vi.fn(), remove: vi.fn() }
}))
vi.mock('../services/api/brandService', () => ({
  brandService: { getAll: vi.fn() }
}))

const machine: Machine = {
  id: 4, nomCommercial: 'Magnifica', referenceCommerciale: 'ECAM22.110.B',
  description: 'Broyeur automatique', marque: 'DeLonghi'
}

describe('MachinesView — contrat MachineACafeDtoIn', () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.mocked(machineService.getAll).mockResolvedValue([machine])
    vi.mocked(brandService.getAll).mockResolvedValue([{ id: 3, marque: 'DeLonghi' }])
    vi.mocked(machineService.create).mockResolvedValue(machine)
  })

  it('bloque la création sans référence ou description exigées par Spring', async () => {
    const wrapper = mount(MachinesView)
    await flushPromises()

    await wrapper.get('input[name="nomCommercial"]').setValue('Nouvelle machine')
    await wrapper.get('select[name="marqueId"]').setValue('3')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeDefined()
    await wrapper.get('form').trigger('submit')
    expect(machineService.create).not.toHaveBeenCalled()
  })

  it('transmet les quatre champs obligatoires à machineService', async () => {
    const wrapper = mount(MachinesView)
    await flushPromises()

    await wrapper.get('input[name="nomCommercial"]').setValue('Nouvelle machine')
    await wrapper.get('input[name="referenceCommerciale"]').setValue('REF-123')
    await wrapper.get('textarea[name="description"]').setValue('Broyeur automatique')
    await wrapper.get('select[name="marqueId"]').setValue('3')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeUndefined()

    await wrapper.get('form').trigger('submit')
    await flushPromises()

    expect(machineService.create).toHaveBeenCalledOnce()
    expect(machineService.create).toHaveBeenCalledWith({
      nomCommercial: 'Nouvelle machine', referenceCommerciale: 'REF-123',
      description: 'Broyeur automatique', marqueId: 3
    })
    expect(wrapper.text()).toContain('Machine créée.')
  })
})
