<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import ConsumptionCard from '../components/consumption/ConsumptionCard.vue'
import { consumptionService } from '../services/api/consumptionService'
import type { Consumption } from '../types/consumption'

const consumptions = ref<Consumption[]>([])
const loading = ref(true)
const hasError = ref(false)

/**
 * Le Dashboard reste volontairement synthétique : les trois dernières entrées
 * suffisent pour reprendre le fil. L'historique complet appartient à sa View.
 */
const recentConsumptions = computed(() => consumptions.value.slice(-3).reverse())

onMounted(async () => {
  try {
    consumptions.value = await consumptionService.getAll()
  } catch {
    hasError.value = true
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <section class="dashboard">
    <div class="hero">
      <div><p class="eyebrow">JoeCoffee</p><h1>Ton café, simplement.</h1><p>Enregistre une dégustation en quelques secondes et retrouve tes derniers réglages.</p></div>
      <RouterLink class="hero-action" to="/consommations/new">+ Nouvelle consommation</RouterLink>
    </div>

    <section class="recent" aria-labelledby="recent-title">
      <header><div><p class="eyebrow">Dernières dégustations</p><h2 id="recent-title">Récemment</h2></div><RouterLink to="/consommations">Tout voir</RouterLink></header>
      <p v-if="loading" role="status">Chargement…</p>
      <p v-else-if="hasError" role="alert">Les dernières consommations sont indisponibles pour le moment.</p>
      <div v-else-if="recentConsumptions.length === 0" class="empty"><p>Pas encore de dégustation.</p><RouterLink to="/consommations/new">Commencer</RouterLink></div>
      <div v-else class="recent-list"><ConsumptionCard v-for="item in recentConsumptions" :key="item.codeConsommation" :consumption="item" /></div>
    </section>
  </section>
</template>

<style scoped>
.dashboard { width:min(100%,1000px); margin:0 auto; padding:1.5rem; }
.hero { display:grid; grid-template-columns:1fr auto; align-items:end; gap:2rem; padding:2rem; border-radius:1.25rem; background:#f3eee8; }
.hero h1 { margin:.25rem 0; font-size:clamp(2rem,5vw,3.5rem); }
.hero p { max-width:600px; }
.eyebrow { margin:0; text-transform:uppercase; letter-spacing:.08em; font-size:.75rem; opacity:.65; }
.hero-action { padding:.9rem 1.1rem; border-radius:.8rem; text-decoration:none; font-weight:700; white-space:nowrap; }
.recent { margin-top:2rem; }
.recent > header { display:flex; align-items:end; justify-content:space-between; margin-bottom:1rem; }
.recent h2 { margin:.2rem 0 0; }
.recent-list { display:grid; gap:.8rem; }
.empty { padding:1.5rem; border:1px dashed #bbb; border-radius:1rem; text-align:center; }
@media (max-width:650px) { .dashboard { padding:1rem 1rem 6rem; } .hero { grid-template-columns:1fr; padding:1.4rem; } .hero-action { text-align:center; } }
</style>
