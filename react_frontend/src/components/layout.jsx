// src/components/Layout.jsx

import React from 'react';

const Layout = ({ children }) => {
  return (
    <div className="wrapper">
      {/* Add your AdminLTE sidebar, header, etc. here */}
      <aside className="main-sidebar sidebar-dark-primary elevation-4">
        <a href="/" className="brand-link">
          <span className="brand-text font-weight-light">AdminLTE</span>
        </a>
        {/* Sidebar Menu */}
        <div className="sidebar">
          {/* Add sidebar content here */}
        </div>
      </aside>
      <div className="content-wrapper">
        <section className="content">{children}</section>
      </div>
    </div>
  );
};

export default Layout;