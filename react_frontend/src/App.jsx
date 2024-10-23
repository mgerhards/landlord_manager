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
          <ul className="navbar-nav">
            <li className="nav-item">
              <a className="nav-link" data-lte-toggle="sidebar" href="#" role="button">
                <i className="bi bi-list"></i>
              </a>
            </li>
          </ul>
          <ul className='navbar-nav ms-auto'>
            <li className="nav-item dropdown user-menu">
              <img src="@node_modules/admin-lte/dist/assets/img/user2-160x160.jpg" class="user-image rounded-circle shadow" alt="User Image" data-astro-source-file="C:/Users/gerha/workspace/AdminLTE/src/html/components/dashboard/_topbar.astro" data-astro-source-loc="169:12" />
              <span class="d-none d-md-inline" data-astro-source-file="C:/Users/gerha/workspace/AdminLTE/src/html/components/dashboard/_topbar.astro" data-astro-source-loc="174:44">Alexander Pierce</span>
             </li>
          </ul>
        </div>
      </nav>
      <Sidebar />
    </div>
  );
};

export default App;