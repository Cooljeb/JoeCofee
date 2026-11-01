// @vitest-environment jsdom
import { beforeEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import CoffeesView from './CoffeesView.vue'
import { coffeeService } from '../services/api/coffeeService'
import type { Coffee } from '../types/coffee'

// La View est testée via son service métier, sans inventer de réponse Spring.
vi.mock('../services/api/coffeeService', () => ({
  coffeeService: { getAll: vi.fn(), create: vi.fn(), update: vi.fn(), remove: vi.fn() }
}))

const coffee: Coffee = {
  id: 1, nomCafe: 'Moka', description: 'Notes de cacao', typeCafe: 'ARABICA',
  labelCafe: null, commercant: 2, commercantNom: 'Torréfacteur', commercantType: 'Artisan'
}

describe('CoffeesView — contrat CafeDtoIn', () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.mocked(coffeeService.getAll).mockResolvedValue([coffee])
    vi.mocked(coffeeService.create).mockResolvedValue(coffee)
  })

  it('refuse les champs incomplets et un identifiant commerçant décimal', async () => {
    const wrapper = mount(CoffeesView)
    await flushPromises()

    await wrapper.get('input[name="nomCafe"]').setValue('Co')
    await wrapper.get('textarea[name="description"]').setValue('abc')
    await wrapper.get('input[name="commercant"]').setValue('1.5')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeDefined()
    await wrapper.get('form').trigger('submit')
    expect(coffeeService.create).not.toHaveBeenCalled()
  })

  it('envoie les valeurs exactes des enums Spring et les longueurs valides', async () => {
    const wrapper = mount(CoffeesView)
    await flushPromises()

    await wrapper.get('input[name="nomCafe"]').setValue('Colombie')
    await wrapper.get('textarea[name="description"]').setValue('Notes de chocolat et noisette')
    await wrapper.get('input[name="commercant"]').setValue('2')
    await wrapper.get('select[name="typeCafe"]').setValue('ROBUSTA')
    await wrapper.get('select[name="labelCafe"]').setValue('BIO')
    expect(wrapper.get('button[type="submit"]').attributes('disabled')).toBeUndefined()

    await wrapper.get('form').trigger('submit')
    await flushPromises()

    expect(coffeeService.create).toHaveBeenCalledOnce()
    expect(coffeeService.create).toHaveBeenCalledWith({
      nomCafe: 'Colombie', description: 'Notes de chocolat et noisette',
      typeCafe: 'ROBUSTA', labelCafe: 'BIO', commercant: 2
    })
    expect(wrapper.text()).toContain('Café créé.')
  })

  it('ne confond pas création réussie et échec du GET de rafraîchissement', async () => {
    vi.mocked(coffeeService.getAll).mockResolvedValueOnce([coffee]).mockRejectedValueOnce(new Error('offline'))
    const wrapper = mount(CoffeesView)
    await flushPromises()

    await wrapper.get('input[name="nomCafe"]').setValue('Colombie')
    await wrapper.get('textarea[name="description"]').setValue('Notes de chocolat et noisette')
    await wrapper.get('input[name="commercant"]').setValue('2')
    await wrapper.get('form').trigger('submit')
    await flushPromises()

    expect(coffeeService.create).toHaveBeenCalledOnce()
    expect(wrapper.get('[role="alert"]').text()).toContain("Café créé, mais impossible d'actualiser")
  })
})
