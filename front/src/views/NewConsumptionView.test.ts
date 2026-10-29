// @vitest-environment jsdom
import { beforeEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import NewConsumptionView from './NewConsumptionView.vue'
import { coffeeService } from '../services/api/coffeeService'
import { machineService } from '../services/api/machineService'
import { consumptionService } from '../services/api/consumptionService'

// Les services sont simulés : on teste les états de la View sans backend ni réseau.
vi.mock('../services/api/coffeeService', () => ({ coffeeService: { getAll: vi.fn() } }))
vi.mock('../services/api/machineService', () => ({ machineService: { getAll: vi.fn() } }))
vi.mock('../services/api/consumptionService', () => ({ consumptionService: { create: vi.fn() } }))
vi.mock('vue-router', () => ({ useRouter: () => ({ push: vi.fn() }) }))

const coffee = {
  id: 1, nomCafe: 'Moka', description: '', typeCafe: '', labelCafe: null,
  commercant: 1, commercantNom: '', commercantType: ''
}
const machine = {
  id: 2, nomCommercial: 'Magnifica', referenceCommerciale: '',
  description: '', marque: 'DeLonghi'
}
const mountView = () => mount(NewConsumptionView, {
  global: { stubs: { RouterLink: true } }
})

describe('NewConsumptionView — états du formulaire', () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.mocked(coffeeService.getAll).mockResolvedValue([coffee])
    vi.mocked(machineService.getAll).mockResolvedValue([machine])
    vi.mocked(consumptionService.create).mockResolvedValue({
      codeConsommation: 8, cafeId: 1, machineACafeId: 2,
      reglageBroyeur: 4, reglageIntensite: 5
    })
  })

  it('guide vers les référentiels quand aucun café ni machine ne sont disponibles', async () => {
    vi.mocked(coffeeService.getAll).mockResolvedValue([])
    vi.mocked(machineService.getAll).mockResolvedValue([])
    const wrapper = mountView()
    await flushPromises()

    expect(wrapper.text()).toContain('au moins un café et une machine')
    expect(wrapper.find('form').exists()).toBe(false)
  })

  it('propose de réessayer après un échec de chargement', async () => {
    vi.mocked(coffeeService.getAll)
      .mockRejectedValueOnce(new Error('indisponible'))
      .mockResolvedValueOnce([coffee])
    const wrapper = mountView()
    await flushPromises()

    expect(wrapper.get('[role="alert"]').text()).toContain('Impossible de charger')
    expect(wrapper.find('form').exists()).toBe(false)

    await wrapper.get('button').trigger('click')
    await flushPromises()

    expect(coffeeService.getAll).toHaveBeenCalledTimes(2)
    expect(wrapper.find('form').exists()).toBe(true)
  })

  it('refuse un réglage hors de la plage Byte acceptée par le formulaire', async () => {
    const wrapper = mountView()
    await flushPromises()

    const selects = wrapper.findAll('select')
    await selects[0].setValue('1')
    await selects[1].setValue('2')
    const inputs = wrapper.findAll('input[type="number"]')
    await inputs[0].setValue('128')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeDefined()

    await inputs[0].setValue('7')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeUndefined()
  })
  it('envoie les réglages sélectionnés au service puis confirme la création', async () => {
    const wrapper = mountView()
    await flushPromises()
    const selects = wrapper.findAll('select')
    await selects[0].setValue('1')
    await selects[1].setValue('2')
    const inputs = wrapper.findAll('input[type="number"]')
    await inputs[0].setValue('4')
    await inputs[1].setValue('5')

    await wrapper.get('form').trigger('submit')
    await flushPromises()

    expect(consumptionService.create).toHaveBeenCalledOnce()
    expect(consumptionService.create).toHaveBeenCalledWith({
      cafeId: 1, machineACafeId: 2, reglageBroyeur: 4, reglageIntensite: 5
    })
    expect(wrapper.text()).toContain('Consommation enregistrée')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeDefined()
  })

  it('réactive le formulaire après un échec de création', async () => {
    vi.mocked(consumptionService.create).mockRejectedValueOnce(new Error('serveur indisponible'))
    const wrapper = mountView()
    await flushPromises()
    const selects = wrapper.findAll('select')
    await selects[0].setValue('1')
    await selects[1].setValue('2')

    await wrapper.get('form').trigger('submit')
    await flushPromises()

    expect(wrapper.get('[role="alert"]').text()).toContain("n'a pas pu être enregistrée")
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeUndefined()
  })

})
