import React from 'react';

const App = () => {
  return (
    <div className="wrapper">
      {/* Header */}
      <nav className="main-header navbar navbar-expand navbar-white navbar-light">
        <ul className="navbar-nav">
          <li className="nav-item">
            <a className="nav-link" data-widget="pushmenu" href="#" role="button">
              <i className="fas fa-bars"></i>
            </a>
          </li>
          <li className="nav-item d-none d-sm-inline-block">
            <a href="/" className="nav-link">Home</a>
          </li>
        </ul>
      </nav>

      {/* Sidebar */}
      <aside className="main-sidebar sidebar-dark-primary elevation-4">
        <a href="/" className="brand-link">
          <span className="brand-text font-weight-light">AdminLTE 3</span>
        </a>
        <div className="sidebar">
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

      {/* Content Wrapper */}
      <div className="content-wrapper">
        <section className="content">
          <div className="container-fluid">
            <h1>Welcome to AdminLTE 3</h1>
          </div>
        </section>
      </div>

      {/* Footer */}
      <footer className="main-footer">
        <div className="float-right d-none d-sm-inline">
          Anything you want
        </div>
        <strong>Copyright &copy; 2023 <a href="/">Your Company</a>.</strong> All rights reserved.
      </footer>
    </div>
  );
};

export default App;