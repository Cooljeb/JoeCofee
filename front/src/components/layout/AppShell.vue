<script setup lang="ts">
/**
 * AppShell porte uniquement la structure générale de l'application.
 * Les écrans métier sont injectés dans <slot /> : le layout ne dépend donc
 * ni des consommations, ni des cafés, ni des machines.
 */
const navigation = [
  { to: '/', label: 'Accueil', icon: '⌂' },
  { to: '/consommations', label: 'Historique', icon: '☕' },
  { to: '/cafes', label: 'Cafés', icon: '◉' },
  { to: '/machines', label: 'Machines', icon: '⚙' }
]
</script>

<template>
  <div class="app-shell">
    <aside class="desktop-nav" aria-label="Navigation principale">
      <RouterLink class="brand" to="/">JoeCoffee</RouterLink>
      <nav>
        <RouterLink v-for="item in navigation" :key="item.to" :to="item.to" class="nav-link">
          <span aria-hidden="true">{{ item.icon }}</span>{{ item.label }}
        </RouterLink>
      </nav>
      <RouterLink class="primary-action" to="/consommations/new">+ Nouvelle consommation</RouterLink>
    </aside>

    <main class="app-content"><slot /></main>

    <!-- Sur téléphone la navigation descend au pouce au lieu de réduire la sidebar desktop. -->
    <nav class="mobile-nav" aria-label="Navigation mobile">
      <RouterLink v-for="item in navigation" :key="item.to" :to="item.to" class="mobile-link">
        <span aria-hidden="true">{{ item.icon }}</span><small>{{ item.label }}</small>
      </RouterLink>
      <RouterLink class="mobile-add" to="/consommations/new" aria-label="Nouvelle consommation">+</RouterLink>
    </nav>
  </div>
</template>
