import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

/** Le même plugin compile les SFC Vue en dev, au build et dans Vitest. */
export default defineConfig({
  plugins: [vue()]
})
