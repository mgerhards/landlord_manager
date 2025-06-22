import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

import { viteStaticCopy } from 'vite-plugin-static-copy'

// https://vitejs.dev/config/
export default defineConfig({
  preview: {
    host: '0.0.0.0',
    host: true,
    port: 4173,
    strictPort: true,
  },
  server: {
    host: '0.0.0.0',
    host: true,
    port: 4173,
    strictPort: true,
    watch: {
      usePolling: true,
    }
  },
  plugins: [react(),
    viteStaticCopy({
      targets: [
        {
          src: 'node_modules/jquery/dist/jquery.min.js',
          dest: 'libs/jquery'
        },
        {
          src: 'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js',
          dest: 'libs/bootstrap'
        },
        {
          src: 'node_modules/admin-lte/dist/js/adminlte.min.js',
          dest: 'libs/admin-lte'
        },
        {
          src: 'node_modules/bootstrap/dist/css/bootstrap.min.css',
          dest: 'libs/bootstrap'
        },
        {
          src: 'node_modules/@fortawesome/fontawesome-free/css/all.min.css',
          dest: 'libs/fontawesome'
        },
        {
          src: 'node_modules/admin-lte/dist/css/adminlte.min.css',
          dest: 'libs/admin-lte'
        }
      ]
    })

  ],
})
