import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  define: {
    global: {},
  },
  server: {
    port: 5173,
    watch: {
      usePolling: true,
    },
    hmr: {
      port: 35730,
    },
  },
});
