


import React from 'react';

const Header = () => {
  return (

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
        <img src="/user2-160x160.jpg" className="user-image rounded-circle shadow" alt="User Image" data-astro-source-file="C:/Users/gerha/workspace/AdminLTE/src/html/components/dashboard/_topbar.astro" data-astro-source-loc="169:12" />
        <span className="d-none d-md-inline" data-astro-source-file="C:/Users/gerha/workspace/AdminLTE/src/html/components/dashboard/_topbar.astro" data-astro-source-loc="174:44">Alexander Pierce</span>
        </li>
    </ul>
    </div>
    </nav>
    );
}

export default Header;