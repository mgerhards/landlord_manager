import React from 'react';
import Sidebar from './Sidebar';

const Layout = ({ children }) => {
  return (
    <div className="wrapper">
      <Sidebar />
      <div className="content-wrapper">
        <div className="container-fluid">
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

          {/* Content Wrapper */}
         
            <section className="content">
              <div className="container-fluid">
                {children}
              </div>
            </section>
   
        </div>
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

export default Layout;