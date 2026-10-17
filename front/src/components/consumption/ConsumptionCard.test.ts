// @vitest-environment jsdom
import { mount } from '@vue/test-utils'
import { describe, expect, it } from 'vitest'
import ConsumptionCard from './ConsumptionCard.vue'

describe('ConsumptionCard', () => {
  it('rend les données métier sans appel API', () => {
    const wrapper = mount(ConsumptionCard, {
      props: {
        consumption: {
          codeConsommation: 42,
          cafeId: 3,
          machineACafeId: 5,
          reglageBroyeur: 7,
          reglageIntensite: 4
        }
      }
    })

    expect(wrapper.text()).toContain('#42')
    expect(wrapper.text()).toContain('Café 3')
    expect(wrapper.text()).toContain('Machine 5')
    expect(wrapper.text()).toContain('7')
    expect(wrapper.text()).toContain('4')
  })
})
