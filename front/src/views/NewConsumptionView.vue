<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { coffeeService } from '../services/api/coffeeService'
import { machineService } from '../services/api/machineService'
import { consumptionService } from '../services/api/consumptionService'
import type { Coffee } from '../types/coffee'
import type { Machine } from '../types/machine'

const router = useRouter()

/**
 * `ref` est l'équivalent Vue d'une valeur observable : lorsque sa propriété
 * `.value` change dans le script, Vue met automatiquement le template à jour.
 */
const coffees = ref<Coffee[]>([])
const machines = ref<Machine[]>([])
const cafeId = ref<number | null>(null)
const machineACafeId = ref<number | null>(null)
const reglageBroyeur = ref(1)
const reglageIntensite = ref(1)
const loading = ref(true)
const submitting = ref(false)
const errorMessage = ref('')
const success = ref(false)

/**
 * `computed` dérive une valeur de l'état existant. Ici il évite de dupliquer
 * la règle de validation entre le bouton et la méthode de soumission.
 */
const canSubmit = computed(() =>
  cafeId.value !== null &&
  machineACafeId.value !== null &&
  reglageBroyeur.value > 0 &&
  reglageIntensite.value > 0 &&
  !submitting.value
)

/** Charge en parallèle les deux référentiels nécessaires au formulaire. */
onMounted(async () => {
  try {
    ;[coffees.value, machines.value] = await Promise.all([
      coffeeService.getAll(),
      machineService.getAll()
    ])
  } catch {
    errorMessage.value = 'Impossible de charger les cafés et les machines.'
  } finally {
    loading.value = false
  }
})

async function submitConsumption() {
  if (!canSubmit.value || cafeId.value === null || machineACafeId.value === null) return

  errorMessage.value = ''
  success.value = false
  submitting.value = true // bloque immédiatement un second clic et donc un double POST

  try {
    await consumptionService.create({
      cafeId: cafeId.value,
      machineACafeId: machineACafeId.value,
      reglageBroyeur: reglageBroyeur.value,
      reglageIntensite: reglageIntensite.value
    })
    success.value = true

    // Une courte confirmation garde un feedback visible avant d'afficher l'historique.
    window.setTimeout(() => router.push('/consommations'), 650)
  } catch {
    errorMessage.value = "La consommation n'a pas pu être enregistrée. Réessaie."
    submitting.value = false
  }
}
</script>

<template>
  <section class="new-consumption">
    <header>
      <p class="eyebrow">Nouvelle dégustation</p>
      <h1>Enregistrer une consommation</h1>
      <p>Choisis ton café et ta machine, puis indique tes réglages.</p>
    </header>

    <p v-if="loading" role="status">Chargement des cafés et machines…</p>

    <form v-else class="consumption-form" @submit.prevent="submitConsumption">
      <label>
        Café
        <select v-model="cafeId" required>
          <option :value="null" disabled>Sélectionner un café</option>
          <option v-for="coffee in coffees" :key="coffee.id" :value="coffee.id">
            {{ coffee.nomCafe }}
          </option>
        </select>
      </label>

      <label>
        Machine
        <select v-model="machineACafeId" required>
          <option :value="null" disabled>Sélectionner une machine</option>
          <option v-for="machine in machines" :key="machine.id" :value="machine.id">
            {{ machine.nomCommercial }} — {{ machine.marque }}
          </option>
        </select>
      </label>

      <div class="settings-grid">
        <label>
          Réglage broyeur
          <input v-model.number="reglageBroyeur" type="number" min="1" required />
        </label>
        <label>
          Intensité
          <input v-model.number="reglageIntensite" type="number" min="1" required />
        </label>
      </div>

      <p v-if="errorMessage" class="feedback error" role="alert">{{ errorMessage }}</p>
      <p v-if="success" class="feedback success" role="status">✓ Consommation enregistrée !</p>

      <button class="submit-button" type="submit" :disabled="!canSubmit">
        {{ submitting ? 'Enregistrement…' : 'Enregistrer la consommation' }}
      </button>
    </form>
  </section>
</template>

<style scoped>
.new-consumption { width: min(100%, 720px); margin: 0 auto; padding: 1.25rem; }
header { margin-bottom: 1.5rem; }
.eyebrow { font-size: .8rem; text-transform: uppercase; letter-spacing: .08em; opacity: .7; }
.consumption-form { display: grid; gap: 1rem; }
label { display: grid; gap: .45rem; font-weight: 600; }
select, input { min-height: 46px; padding: .7rem; border: 1px solid #bbb; border-radius: .7rem; font: inherit; }
.settings-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
.submit-button { min-height: 50px; border: 0; border-radius: .8rem; padding: .8rem 1rem; font: inherit; font-weight: 700; cursor: pointer; }
.submit-button:disabled { cursor: not-allowed; opacity: .55; }
.feedback { padding: .75rem; border-radius: .7rem; }
.success { animation: confirm .3s ease-out; }
@keyframes confirm { from { transform: scale(.97); opacity: 0; } to { transform: scale(1); opacity: 1; } }
@media (max-width: 600px) {
  .new-consumption { padding: 1rem 1rem 6rem; }
  .settings-grid { grid-template-columns: 1fr; }
  .submit-button { position: sticky; bottom: 5rem; width: 100%; }
}
@media (prefers-reduced-motion: reduce) { .success { animation: none; } }
</style>
