<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { coffeeService } from '../services/api/coffeeService'
import type { Coffee } from '../types/coffee'

const coffees = ref<Coffee[]>([])
const loading = ref(true)
const errorMessage = ref('')

/**
 * La View orchestre le cas d'usage (charger puis afficher). Elle ne connaît
 * volontairement aucune URL : le contrat HTTP reste dans coffeeService.
 */
async function loadCoffees() {
  loading.value = true
  errorMessage.value = ''
  try {
    coffees.value = await coffeeService.getAll()
  } catch {
    errorMessage.value = 'Impossible de charger les cafés.'
  } finally {
    loading.value = false
  }
}

onMounted(loadCoffees)
</script>

<template>
  <section class="catalog-page">
    <header class="page-header">
      <div>
        <p class="eyebrow">Référentiel</p>
        <h1>Mes cafés</h1>
        <p>Les cafés disponibles pour tes prochaines dégustations.</p>
      </div>
      <RouterLink class="primary-action" to="/consommations/new">+ Nouvelle consommation</RouterLink>
    </header>

    <p v-if="loading" role="status">Chargement des cafés…</p>
    <div v-else-if="errorMessage" class="state-card" role="alert">
      <p>{{ errorMessage }}</p>
      <button type="button" @click="loadCoffees">Réessayer</button>
    </div>
    <div v-else-if="coffees.length === 0" class="state-card">
      <h2>Aucun café</h2>
      <p>Le référentiel est vide pour le moment.</p>
    </div>

    <div v-else class="catalog-grid">
      <article v-for="coffee in coffees" :key="coffee.id" class="catalog-card">
        <div class="card-heading">
          <h2>{{ coffee.nomCafe }}</h2>
          <span class="badge">{{ coffee.typeCafe }}</span>
        </div>
        <p>{{ coffee.description || 'Aucune description.' }}</p>
        <dl>
          <div><dt>Label</dt><dd>{{ coffee.labelCafe || '—' }}</dd></div>
          <div><dt>Commerçant</dt><dd>{{ coffee.commercantNom || `#${coffee.commercant}` }}</dd></div>
          <div><dt>Type</dt><dd>{{ coffee.commercantType || '—' }}</dd></div>
        </dl>
      </article>
    </div>
  </section>
</template>

<style scoped>
.catalog-page { padding: 1.25rem; }
.page-header { display:flex; justify-content:space-between; gap:1rem; align-items:end; margin-bottom:1.5rem; }
.eyebrow { text-transform:uppercase; letter-spacing:.08em; font-size:.8rem; opacity:.65; }
.primary-action { padding:.75rem 1rem; border-radius:.75rem; text-decoration:none; font-weight:700; }
.catalog-grid { display:grid; grid-template-columns:repeat(auto-fit,minmax(260px,1fr)); gap:1rem; }
.catalog-card,.state-card { border:1px solid #ddd; border-radius:1rem; padding:1rem; }
.card-heading { display:flex; justify-content:space-between; gap:.75rem; align-items:start; }
.badge { font-size:.8rem; padding:.25rem .5rem; border:1px solid #ccc; border-radius:999px; }
dl { display:grid; gap:.45rem; margin-bottom:0; }
dl div { display:flex; justify-content:space-between; gap:1rem; }
dt { opacity:.65; } dd { margin:0; text-align:right; }
@media (max-width:600px) { .catalog-page { padding:1rem 1rem 6rem; } .page-header { align-items:stretch; flex-direction:column; } .primary-action { text-align:center; } }
</style>
