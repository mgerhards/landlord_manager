import React from 'react';

// Import CSS files
import '@bootstrap-css'
import '@fontawesome-css'
import '@adminlte-css'

// Import JS files
import '@bootstrap-js'
import '@adminlte-js'

const App = () => {
  return (
    <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
      <nav className="app-header navbar navbar-expand bg-body">
            <div className="container-fluid ">
                <ul className="navbar-nav ms-auto">
                    <li className="nav-item"> <a className="nav-link" data-lte-toggle="sidebar" href="#" role="button"> <i className="bi bi-list"></i> </a> </li>
                    <li className="nav-item d-none d-md-block"> <a href="#" className="nav-link">Home</a> </li>
                    <li className="nav-item d-none d-md-block"> <a href="#" className="nav-link">Contact</a> </li>
                </ul>
            </div>
      </nav>
      <aside className="app-sidebar bg-body-secondary shadow" data-bs-theme="dark">
            <div className="sidebar-brand">
              <a className="brand-link" href="/dist/pages/"> 
                <img src="../../dist/assets/img/AdminLTELogo.png" alt="AdminLTE Logo" className="brand-image opacity-75 shadow"/> 
                <span className="brand-text fw-light">AdminLTE 4</span> 
              </a>
            </div>
     
            <div className="sidebar-wrapper app-sidebar bg-body-secondary shadow" data-overlayscrollbars="host">
              <nav className="mt-2"> 
                            <ul className="nav sidebar-menu flex-column" data-lte-toggle="treeview" role="menu" data-accordion="false">
                                <li className="nav-item menu-open"> <a href="#" className="nav-link active"> <i className="nav-icon bi bi-speedometer"></i>
                                        <p>
                                            Dashboard
                                            <i className="nav-arrow bi bi-chevron-right"></i>
                                        </p>
                                    </a>
                                </li>
                            </ul>
              </nav>
            </div>
      </aside>
    </div>
  );
};

export default App;