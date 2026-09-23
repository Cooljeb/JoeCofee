<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ConsumptionCard from '../components/consumption/ConsumptionCard.vue'
import { consumptionService } from '../services/api/consumptionService'
import type { Consumption } from '../types/consumption'

const consumptions = ref<Consumption[]>([])
const loading = ref(true)
const errorMessage = ref('')

/**
 * La View orchestre le cas d'usage : elle appelle le service puis confie
 * l'affichage à ConsumptionCard. Elle ne connaît donc aucune URL REST.
 */
async function loadConsumptions() {
  loading.value = true
  errorMessage.value = ''
  try {
    consumptions.value = await consumptionService.getAll()
  } catch {
    errorMessage.value = "Impossible de charger l'historique."
  } finally {
    loading.value = false
  }
}

onMounted(loadConsumptions)
</script>

<template>
  <section class="page">
    <header class="page-header">
      <div><p class="eyebrow">Journal</p><h1>Historique</h1></div>
      <RouterLink class="primary-action" to="/consommations/new">+ Nouvelle consommation</RouterLink>
    </header>

    <p v-if="loading" role="status">Chargement de l'historique…</p>
    <div v-else-if="errorMessage" class="state state--error" role="alert">
      <p>{{ errorMessage }}</p><button type="button" @click="loadConsumptions">Réessayer</button>
    </div>
    <div v-else-if="consumptions.length === 0" class="state">
      <h2>Aucune consommation</h2>
      <p>Ta première dégustation apparaîtra ici.</p>
      <RouterLink to="/consommations/new">Enregistrer la première</RouterLink>
    </div>
    <div v-else class="consumption-list">
      <ConsumptionCard v-for="item in consumptions" :key="item.codeConsommation" :consumption="item" />
    </div>
  </section>
</template>

<style scoped>
.page { width:min(100%,960px); margin:0 auto; padding:1.5rem; }
.page-header { display:flex; justify-content:space-between; align-items:end; gap:1rem; margin-bottom:1.5rem; }
.eyebrow { margin:0; text-transform:uppercase; letter-spacing:.08em; font-size:.75rem; opacity:.65; }
h1 { margin:.2rem 0 0; }
.primary-action { padding:.75rem 1rem; border-radius:.8rem; text-decoration:none; font-weight:700; }
.consumption-list { display:grid; gap:.8rem; }
.state { padding:2rem; text-align:center; border:1px dashed #bbb; border-radius:1rem; }
.state--error { border-style:solid; }
@media (max-width:600px) { .page { padding:1rem 1rem 6rem; } .page-header { align-items:start; flex-direction:column; } .primary-action { width:100%; text-align:center; box-sizing:border-box; } }
</style>
