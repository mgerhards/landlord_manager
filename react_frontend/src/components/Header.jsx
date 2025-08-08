import PropTypes from 'prop-types';

const Header = ({ onLogout }) => {
  return (
    <nav className="app-header navbar navbar-expand bg-body">
      <div className="container-fluid">
        <ul className="navbar-nav">
          <li className="nav-item">
            <a className="nav-link" data-lte-toggle="sidebar" href="#" role="button">
              <i className="bi bi-list"></i>
            </a>
          </li>
        </ul>
        <ul className='navbar-nav ms-auto'>
          <li className="nav-item dropdown user-menu">
            <a className="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">
              <img src="/user2-160x160.jpg" className="user-image rounded-circle shadow" alt="User Image" />
              <span className="d-none d-md-inline">User</span>
            </a>
            <ul className="dropdown-menu dropdown-menu-lg dropdown-menu-end">
              <li className="user-header bg-primary">
                <img src="/user2-160x160.jpg" className="rounded-circle shadow" alt="User Image" />
                <p>
                  Landlord Manager User
                  <small>Property Management</small>
                </p>
              </li>
              <li className="user-footer">
                <div className="float-start">
                  <a href="#" className="btn btn-default btn-flat">Profile</a>
                </div>
                <div className="float-end">
                  <button 
                    onClick={onLogout} 
                    className="btn btn-default btn-flat"
                    type="button"
                  >
                    Sign out
                  </button>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  );
};

Header.propTypes = {
  onLogout: PropTypes.func.isRequired,
};

export default Header;