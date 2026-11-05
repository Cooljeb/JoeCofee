// @vitest-environment jsdom
import { describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import AppShell from './AppShell.vue'

describe('AppShell — navigation clavier', () => {
  it('propose un lien d’évitement vers le contenu principal', () => {
    // Le layout ne dépend d’aucun écran métier : le slot suffit à tester la structure.
    const wrapper = mount(AppShell, {
      slots: { default: '<h1>Bienvenue</h1>' },
      global: { stubs: { RouterLink: true } }
    })

    expect(wrapper.get('a.skip-link').attributes('href')).toBe('#main-content')
    expect(wrapper.get('main#main-content').attributes('tabindex')).toBe('-1')
    expect(wrapper.get('main').text()).toContain('Bienvenue')
    expect(wrapper.find('nav[aria-label="Navigation mobile"]').exists()).toBe(true)
  })
})
