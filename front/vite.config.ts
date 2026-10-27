import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

/** Le même plugin compile les SFC Vue en dev, au build et dans Vitest. */
export default defineConfig({
  plugins: [vue()],
  // Le navigateur reste sur l'origine Vite ; seul le serveur de développement
  // contacte Spring sur :8080. Aucun CORS backend requis pour ce parcours local.
  server: {
    proxy: {
      '/api': { target: 'http://localhost:8080', changeOrigin: true }
    }
  }
})
