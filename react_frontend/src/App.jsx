import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css'; 
import 'admin-lte/dist/css/adminlte.min.css';

import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'admin-lte/dist/js/adminlte.min.js';
import Layout from './components/Layout';
function App() {
 

  return (
    <>
      <Layout>
        <h1>Welcome to AdminLTE with React!</h1>
        {/* Add more components/content here */}
      </Layout>
    </>
  )
}

export default App
