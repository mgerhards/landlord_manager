import React from 'react';

// Import CSS files
import '@bootstrap-css'
import '@fontawesome-css'
import '@adminlte-css'

// Import JS files
import '@bootstrap-js'
import '@adminlte-js'

import Sidebar from './components/Sidebar';
import "./App.css"

const App = () => {
 

  return (
    <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
      <nav className="app-header navbar navbar-expand bg-body">
        <div className="container-fluid ">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <a className="nav-link" data-lte-toggle="sidebar" href="#" role="button">
                <i className="bi bi-list"></i>
              </a>
            </li>
            <li className="nav-item d-none d-md-block">
              <a href="#" className="nav-link">Home</a>
            </li>
            <li className="nav-item d-none d-md-block">
              <a href="#" className="nav-link">Contact</a>
            </li>
          </ul>
        </div>
      </nav>
      <Sidebar />
    </div>
  );
};

export default App;