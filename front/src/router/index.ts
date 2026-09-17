import { createRouter, createWebHistory } from 'vue-router'

/**
 * Le routeur décrit les écrans métier du MVP.
 * Les composants restent chargés à la demande : le navigateur ne télécharge
 * donc le code d'un écran secondaire que lorsque l'utilisateur y accède.
 */
export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'dashboard', component: () => import('../views/DashboardView.vue') },
    { path: '/consommations', name: 'consumptions', component: () => import('../views/ConsumptionsView.vue') },
    { path: '/consommations/new', name: 'new-consumption', component: () => import('../views/NewConsumptionView.vue') },
    { path: '/cafes', name: 'coffees', component: () => import('../views/CoffeesView.vue') },
    { path: '/machines', name: 'machines', component: () => import('../views/MachinesView.vue') }
  ]
})
