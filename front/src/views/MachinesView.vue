<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { machineService } from '../services/api/machineService'
import type { Machine } from '../types/machine'

const machines = ref<Machine[]>([])
const loading = ref(true)
const errorMessage = ref('')

/** Même pattern que CoffeesView : la View pilote l'écran, le service pilote HTTP. */
async function loadMachines() {
  loading.value = true
  errorMessage.value = ''
  try {
    machines.value = await machineService.getAll()
  } catch {
    errorMessage.value = 'Impossible de charger les machines.'
  } finally {
    loading.value = false
  }
}

onMounted(loadMachines)
</script>

<template>
  <section class="catalog-page">
    <header class="page-header">
      <div>
        <p class="eyebrow">Référentiel</p>
        <h1>Mes machines</h1>
        <p>Les machines disponibles pour enregistrer une consommation.</p>
      </div>
      <RouterLink class="primary-action" to="/consommations/new">+ Nouvelle consommation</RouterLink>
    </header>

    <p v-if="loading" role="status">Chargement des machines…</p>
    <div v-else-if="errorMessage" class="state-card" role="alert">
      <p>{{ errorMessage }}</p>
      <button type="button" @click="loadMachines">Réessayer</button>
    </div>
    <div v-else-if="machines.length === 0" class="state-card">
      <h2>Aucune machine</h2>
      <p>Le référentiel est vide pour le moment.</p>
    </div>

    <div v-else class="catalog-grid">
      <article v-for="machine in machines" :key="machine.id" class="catalog-card">
        <div class="card-heading">
          <h2>{{ machine.nomCommercial }}</h2>
          <span class="badge">{{ machine.marque }}</span>
        </div>
        <p>{{ machine.description || 'Aucune description.' }}</p>
        <dl>
          <div><dt>Référence</dt><dd>{{ machine.referenceCommerciale || '—' }}</dd></div>
          <div><dt>Identifiant</dt><dd>#{{ machine.id }}</dd></div>
        </dl>
      </article>
    </div>
  </section>
</template>

<style scoped>
.catalog-page { padding:1.25rem; }
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
