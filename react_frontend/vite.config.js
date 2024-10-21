import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@bootstrap-css': resolve(__dirname, 'node_modules/bootstrap/dist/css/bootstrap.min.css'),
      '@fontawesome-css': resolve(__dirname, 'node_modules/@fortawesome/fontawesome-free/css/all.min.css'),
      '@adminlte-css': resolve(__dirname, 'node_modules/admin-lte/dist/css/adminlte.min.css'),
      '@bootstrap-js': resolve(__dirname, 'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js'),
      '@adminlte-js': resolve(__dirname, 'node_modules/admin-lte/dist/js/adminlte.min.js'),
    }
  }
})
