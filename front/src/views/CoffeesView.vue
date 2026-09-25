<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { coffeeService } from '../services/api/coffeeService'
import type { Coffee, CoffeeInput } from '../types/coffee'

const coffees = ref<Coffee[]>([])
const loading = ref(true)
const saving = ref(false)
const errorMessage = ref('')
const successMessage = ref('')
const editingId = ref<number | null>(null)
const form = reactive<CoffeeInput>({ nomCafe: '', description: '', typeCafe: '', labelCafe: null, commercant: 0 })

/** Le formulaire reste un état UI ; seul coffeeService connaît les détails REST. */
function resetForm() {
  editingId.value = null
  Object.assign(form, { nomCafe: '', description: '', typeCafe: '', labelCafe: null, commercant: 0 })
}

async function loadCoffees() {
  loading.value = true
  errorMessage.value = ''
  try { coffees.value = await coffeeService.getAll() }
  catch { errorMessage.value = 'Impossible de charger les cafés.' }
  finally { loading.value = false }
}

function editCoffee(coffee: Coffee) {
  editingId.value = coffee.id
  Object.assign(form, { nomCafe: coffee.nomCafe, description: coffee.description, typeCafe: coffee.typeCafe, labelCafe: coffee.labelCafe, commercant: coffee.commercant })
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

async function saveCoffee() {
  if (!form.nomCafe.trim() || form.commercant <= 0 || saving.value) return
  saving.value = true
  errorMessage.value = ''
  successMessage.value = ''
  try {
    if (editingId.value === null) await coffeeService.create({ ...form })
    else await coffeeService.update(editingId.value, { ...form })
    successMessage.value = editingId.value === null ? 'Café créé.' : 'Café modifié.'
    resetForm()
    await loadCoffees()
  } catch { errorMessage.value = "L'enregistrement du café a échoué." }
  finally { saving.value = false }
}

async function removeCoffee(coffee: Coffee) {
  if (!window.confirm(`Supprimer le café « ${coffee.nomCafe} » ?`)) return
  try {
    await coffeeService.remove(coffee.id)
    successMessage.value = 'Café supprimé.'
    await loadCoffees()
  } catch { errorMessage.value = 'La suppression du café a échoué.' }
}

onMounted(loadCoffees)
</script>

<template>
  <section class="catalog-page">
    <header class="page-header"><div><p class="eyebrow">Référentiel</p><h1>Mes cafés</h1><p>Gère les cafés disponibles pour tes dégustations.</p></div></header>

    <form class="editor" @submit.prevent="saveCoffee">
      <h2>{{ editingId === null ? 'Ajouter un café' : 'Modifier le café' }}</h2>
      <div class="form-grid">
        <label>Nom<input v-model.trim="form.nomCafe" required /></label>
        <label>Type<input v-model.trim="form.typeCafe" /></label>
        <label>Label<input v-model.trim="form.labelCafe" /></label>
        <label>ID commerçant<input v-model.number="form.commercant" type="number" min="1" required /></label>
        <label class="wide">Description<textarea v-model.trim="form.description" rows="3" /></label>
      </div>
      <div class="actions"><button type="submit" :disabled="saving">{{ saving ? 'Enregistrement…' : (editingId === null ? 'Ajouter' : 'Enregistrer') }}</button><button v-if="editingId !== null" type="button" @click="resetForm">Annuler</button></div>
    </form>

    <p v-if="successMessage" class="feedback" role="status">{{ successMessage }}</p>
    <div v-if="errorMessage" class="state-card" role="alert"><p>{{ errorMessage }}</p><button type="button" @click="loadCoffees">Réessayer</button></div>
    <p v-else-if="loading" role="status">Chargement des cafés…</p>
    <div v-else-if="coffees.length === 0" class="state-card"><h2>Aucun café</h2></div>

    <div v-else class="catalog-grid">
      <article v-for="coffee in coffees" :key="coffee.id" class="catalog-card">
        <div class="card-heading"><h2>{{ coffee.nomCafe }}</h2><span class="badge">{{ coffee.typeCafe }}</span></div>
        <p>{{ coffee.description || 'Aucune description.' }}</p>
        <p>{{ coffee.commercantNom || `Commerçant #${coffee.commercant}` }}</p>
        <div class="actions"><button type="button" @click="editCoffee(coffee)">Modifier</button><button type="button" @click="removeCoffee(coffee)">Supprimer</button></div>
      </article>
    </div>
  </section>
</template>

<style scoped>
.catalog-page{padding:1.25rem}.page-header{margin-bottom:1rem}.eyebrow{text-transform:uppercase;letter-spacing:.08em;font-size:.8rem;opacity:.65}.editor,.catalog-card,.state-card{border:1px solid #ddd;border-radius:1rem;padding:1rem}.editor{margin-bottom:1rem}.form-grid{display:grid;grid-template-columns:1fr 1fr;gap:1rem}.wide{grid-column:1/-1}label{display:grid;gap:.4rem;font-weight:600}input,textarea{padding:.7rem;border:1px solid #bbb;border-radius:.65rem;font:inherit}.catalog-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:1rem}.card-heading,.actions{display:flex;justify-content:space-between;gap:.75rem;align-items:center}.actions{justify-content:flex-start;margin-top:.8rem}.badge{font-size:.8rem;padding:.25rem .5rem;border:1px solid #ccc;border-radius:999px}.feedback{padding:.75rem;border-radius:.7rem}@media(max-width:600px){.catalog-page{padding:1rem 1rem 6rem}.form-grid{grid-template-columns:1fr}.wide{grid-column:auto}.actions button{min-height:44px;flex:1}}
@media(prefers-reduced-motion:reduce){*{scroll-behavior:auto!important}}
</style>
