<script setup lang="ts">
import type { Consumption } from '../../types/consumption'

/**
 * Composant de présentation : il reçoit une consommation et l'affiche.
 * Il ne charge aucune donnée lui-même. Cette séparation permet au Dashboard
 * et à l'Historique de réutiliser exactement le même rendu.
 */
defineProps<{ consumption: Consumption }>()
</script>

<template>
  <article class="consumption-card">
    <div>
      <span class="consumption-card__id">#{{ consumption.codeConsommation }}</span>
      <h3>Café {{ consumption.cafeId }}</h3>
      <p>Machine {{ consumption.machineACafeId }}</p>
    </div>
    <dl>
      <div><dt>Broyeur</dt><dd>{{ consumption.reglageBroyeur }}</dd></div>
      <div><dt>Intensité</dt><dd>{{ consumption.reglageIntensite }}</dd></div>
    </dl>
  </article>
</template>

<style scoped>
.consumption-card { display:flex; justify-content:space-between; gap:1rem; padding:1rem; border:1px solid #ddd; border-radius:1rem; background:#fff; }
.consumption-card__id { font-size:.75rem; opacity:.6; }
h3,p { margin:.2rem 0; }
dl { display:flex; gap:1rem; margin:0; }
dl div { text-align:center; }
dt { font-size:.72rem; opacity:.65; }
dd { margin:0; font-weight:700; }
@media (max-width:520px) { .consumption-card { align-items:center; } dl { gap:.65rem; } }
</style>
