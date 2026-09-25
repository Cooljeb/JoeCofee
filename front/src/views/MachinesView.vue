<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { machineService } from '../services/api/machineService'
import type { Machine, MachineInput } from '../types/machine'

const machines = ref<Machine[]>([])
const loading = ref(true)
const saving = ref(false)
const errorMessage = ref('')
const successMessage = ref('')
const editingId = ref<number | null>(null)
const form = reactive<MachineInput>({ nomCommercial: '', referenceCommerciale: '', description: '', marqueId: 0 })

/** `reactive` convient ici à un petit objet formulaire dont les champs restent liés par v-model. */
function resetForm() {
  editingId.value = null
  Object.assign(form, { nomCommercial: '', referenceCommerciale: '', description: '', marqueId: 0 })
}

async function loadMachines() {
  loading.value = true
  errorMessage.value = ''
  try { machines.value = await machineService.getAll() }
  catch { errorMessage.value = 'Impossible de charger les machines.' }
  finally { loading.value = false }
}

function editMachine(machine: Machine) {
  editingId.value = machine.id
  Object.assign(form, {
    nomCommercial: machine.nomCommercial,
    referenceCommerciale: machine.referenceCommerciale,
    description: machine.description,
    // Le DTO OUT expose le nom de marque mais pas son id : on demande donc explicitement l'id attendu par le DTO IN.
    marqueId: 0
  })
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

async function saveMachine() {
  if (!form.nomCommercial.trim() || form.marqueId <= 0 || saving.value) return
  saving.value = true
  errorMessage.value = ''
  successMessage.value = ''
  try {
    if (editingId.value === null) await machineService.create({ ...form })
    else await machineService.update(editingId.value, { ...form })
    successMessage.value = editingId.value === null ? 'Machine créée.' : 'Machine modifiée.'
    resetForm()
    await loadMachines()
  } catch { errorMessage.value = "L'enregistrement de la machine a échoué." }
  finally { saving.value = false }
}

async function removeMachine(machine: Machine) {
  if (!window.confirm(`Supprimer la machine « ${machine.nomCommercial} » ?`)) return
  try {
    await machineService.remove(machine.id)
    successMessage.value = 'Machine supprimée.'
    await loadMachines()
  } catch { errorMessage.value = 'La suppression de la machine a échoué.' }
}

onMounted(loadMachines)
</script>

<template>
  <section class="catalog-page">
    <header class="page-header"><div><p class="eyebrow">Référentiel</p><h1>Mes machines</h1><p>Machines disponibles pour tes consommations.</p></div></header>

    <form class="editor" @submit.prevent="saveMachine">
      <h2>{{ editingId === null ? 'Ajouter une machine' : 'Modifier la machine' }}</h2>
      <div class="form-grid">
        <label>Nom commercial<input v-model.trim="form.nomCommercial" required /></label>
        <label>Référence<input v-model.trim="form.referenceCommerciale" /></label>
        <label>ID marque<input v-model.number="form.marqueId" type="number" min="1" required /></label>
        <label class="wide">Description<textarea v-model.trim="form.description" rows="3" /></label>
      </div>
      <div class="actions"><button type="submit" :disabled="saving">{{ saving ? 'Enregistrement…' : (editingId === null ? 'Ajouter' : 'Enregistrer') }}</button><button v-if="editingId !== null" type="button" @click="resetForm">Annuler</button></div>
    </form>

    <p v-if="successMessage" class="feedback" role="status">{{ successMessage }}</p>
    <div v-if="errorMessage" class="state-card" role="alert"><p>{{ errorMessage }}</p><button type="button" @click="loadMachines">Réessayer</button></div>
    <p v-else-if="loading" role="status">Chargement des machines…</p>
    <div v-else-if="machines.length === 0" class="state-card"><h2>Aucune machine</h2></div>

    <div v-else class="catalog-grid">
      <article v-for="machine in machines" :key="machine.id" class="catalog-card">
        <div class="card-heading"><h2>{{ machine.nomCommercial }}</h2><span class="badge">{{ machine.marque }}</span></div>
        <p>{{ machine.description || 'Aucune description.' }}</p>
        <p>Référence : {{ machine.referenceCommerciale || '—' }}</p>
        <div class="actions"><button type="button" @click="editMachine(machine)">Modifier</button><button type="button" @click="removeMachine(machine)">Supprimer</button></div>
      </article>
    </div>
  </section>
</template>

<style scoped>
.catalog-page{padding:1.25rem}.page-header{margin-bottom:1rem}.eyebrow{text-transform:uppercase;letter-spacing:.08em;font-size:.8rem;opacity:.65}.editor,.catalog-card,.state-card{border:1px solid #ddd;border-radius:1rem;padding:1rem}.editor{margin-bottom:1rem}.form-grid{display:grid;grid-template-columns:1fr 1fr;gap:1rem}.wide{grid-column:1/-1}label{display:grid;gap:.4rem;font-weight:600}input,textarea{padding:.7rem;border:1px solid #bbb;border-radius:.65rem;font:inherit}.catalog-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:1rem}.card-heading,.actions{display:flex;justify-content:space-between;gap:.75rem;align-items:center}.actions{justify-content:flex-start;margin-top:.8rem}.badge{font-size:.8rem;padding:.25rem .5rem;border:1px solid #ccc;border-radius:999px}.feedback{padding:.75rem;border-radius:.7rem}@media(max-width:600px){.catalog-page{padding:1rem 1rem 6rem}.form-grid{grid-template-columns:1fr}.wide{grid-column:auto}.actions button{min-height:44px;flex:1}}
@media(prefers-reduced-motion:reduce){*{scroll-behavior:auto!important}}
</style>
