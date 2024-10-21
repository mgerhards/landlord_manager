import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import './index.css'
// Import CSS files
import '@bootstrap-css'
import '@fontawesome-css'
import '@adminlte-css'

// Import JS files
import '@bootstrap-js'
import '@adminlte-js'
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
