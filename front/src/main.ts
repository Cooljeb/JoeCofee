import { createApp } from 'vue'
import App from './App.vue'
import { router } from './router'
import './styles/main.css'

/**
 * Équivalent du point de démarrage Spring Boot côté Front :
 * on crée l'application Vue, on branche le routeur, puis on monte l'application
 * dans la balise #app définie par index.html.
 */
createApp(App)
  .use(router)
  .mount('#app')
