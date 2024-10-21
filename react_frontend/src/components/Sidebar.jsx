import React from 'react';

const Sidebar = () => {
  return (
    <aside className="main-sidebar sidebar-dark-primary ">
      <a href="/" className="brand-link">
        <span className="brand-text font-weight-light">AdminLTE Dashboard</span>
      </a>
      <div className="sidebar layout-fixed ">
        <nav className="mt-2">
          <ul className="nav nav-pills nav-sidebar flex-column" role="menu" data-accordion="false">
            <li className="nav-item">
              <a href="#" className="nav-link">
                <i className="nav-icon fas fa-tachometer-alt"></i>
                <p>Dashboard</p>
              </a>
            </li>
            <li className="nav-item">
              <a href="#" className="nav-link">
                <i className="nav-icon fas fa-th"></i>
                <p>Widgets</p>
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </aside>
  );
};

export default Sidebar;