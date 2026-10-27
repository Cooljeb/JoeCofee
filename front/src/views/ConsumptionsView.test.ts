// @vitest-environment jsdom
import { beforeEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import ConsumptionsView from './ConsumptionsView.vue'
import { consumptionService } from '../services/api/consumptionService'

// La View est testée sans réseau : seul le service métier est simulé.
vi.mock('../services/api/consumptionService', () => ({
  consumptionService: { getAll: vi.fn() }
}))

const consumption = {
  codeConsommation: 8, cafeId: 1, machineACafeId: 2,
  reglageBroyeur: 4, reglageIntensite: 5
}
const mountView = () => mount(ConsumptionsView, {
  global: { stubs: { RouterLink: true } }
})

describe('ConsumptionsView — historique MVP', () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.mocked(consumptionService.getAll).mockResolvedValue([consumption])
  })

  it('affiche une consommation renvoyée par le service', async () => {
    const wrapper = mountView()
    await flushPromises()

    expect(wrapper.text()).toContain('#8')
    expect(wrapper.text()).toContain('Café 1')
    expect(consumptionService.getAll).toHaveBeenCalledOnce()
  })

  it('propose une nouvelle tentative après une erreur API', async () => {
    vi.mocked(consumptionService.getAll)
      .mockRejectedValueOnce(new Error('indisponible'))
      .mockResolvedValueOnce([consumption])

    const wrapper = mountView()
    await flushPromises()
    expect(wrapper.get('[role="alert"]').text()).toContain("Impossible de charger l'historique")

    await wrapper.get('button').trigger('click')
    await flushPromises()
    expect(consumptionService.getAll).toHaveBeenCalledTimes(2)
    expect(wrapper.text()).toContain('#8')
  })

  it('affiche une invitation à créer la première dégustation si la liste est vide', async () => {
    vi.mocked(consumptionService.getAll).mockResolvedValue([])
    const wrapper = mountView()
    await flushPromises()
    expect(wrapper.text()).toContain('Aucune consommation')
    expect(wrapper.text()).toContain('Enregistrer la première')
  })
})
