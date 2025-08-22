import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar = () => {
  return (
    <aside className="app-sidebar bg-body-secondary shadow" data-bs-theme="dark">
            <div className="sidebar-brand">
              <a className="brand-link" href="/dist/pages/"> 
                <img src="/lm-logo-dark-rect.png" alt="AdminLTE Logo" className="brand-image opacity-75"/> 
                
              </a>
            </div>
     
            <div className="sidebar-wrapper app-sidebar bg-body-secondary shadow" data-overlayscrollbars="host">
              <nav className="mt-2"> 
                  <ul className="nav sidebar-menu flex-column" data-lte-toggle="treeview" role="menu" data-accordion="false">
                      
                        <li className="nav-item">
                        <Link to="/immobilien" className="nav-link">
                            <i className="nav-icon bi bi-buildings-fill"></i>
                            <p>
                              Immobilien
                            </p>
                          </Link>
                        </li>
                        <li className="nav-item">
                        <Link to="/tenants" className="nav-link">
                            <i className="nav-icon bi bi-people-fill"></i>
                            <p>
                              Mieter
                            </p>
                          </Link>
                        </li>
                        <li className="nav-item">
                        <Link to="/companies" className="nav-link">
                            <i className="nav-icon bi bi-tools"></i>
                            <p>
                              Firmen
                            </p>
                          </Link>
                        </li>
                        <li className="nav-item">
                        <Link to="/tickets" className="nav-link">
                            <i className="nav-icon bi bi-ticket-fill"></i>
                            <p>
                              Tickets
                            </p>
                          </Link>
                        </li>
                      </ul>
              </nav>
            </div>
      </aside>
   
  );
};

export default Sidebar;