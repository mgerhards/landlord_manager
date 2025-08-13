import { useEffect, useState, useCallback } from 'react';
import PropTypes from 'prop-types';
import Sidebar from './components/Sidebar.jsx';
import Header from './components/Header.jsx';
import "./App.css"
import { Routes, Route, useNavigate } from 'react-router-dom';
import RealEstateOverview from './components/RealEstate/Overview.jsx';
import RealEstateForm from './components/RealEstate/Form.jsx';
import TennantsOverview from './components/tennants/Overview.jsx';
import CompaniesOverview from './components/companies/Overview.jsx';
import TicketsOverview from './components/tickets/Overview.jsx';
import TicketCreate from './components/tickets/TicketCreate.jsx';
import RealEstateDetails from './components/RealEstate/Details.jsx';
import Login from './Login.jsx';
import { isAuthenticated, verifyToken, logout, apiCall } from './config/auth.js';
import { ENDPOINTS } from './config/api.js';

const Home = () => <h3>Home</h3>;

const App = () => {
  const navigate = useNavigate();
  
  useEffect(() => {
    // Initialize AdminLTE components if necessary
    import('admin-lte');
  }, []);

  const [token, setToken] = useState(localStorage.getItem('token'));
  const [realEstateObject, setRealEstateObject] = useState(null);
  const [error, setError] = useState(null);
  const [isTokenValid, setIsTokenValid] = useState(false);
  const [isCheckingAuth, setIsCheckingAuth] = useState(true);

  // Verify token validity on app start
  useEffect(() => {
    const checkAuthentication = async () => {
      if (isAuthenticated()) {
        try {
          const valid = await verifyToken();
          setIsTokenValid(valid);
          if (!valid) {
            setToken(null);
          }
        } catch (error) {
          console.error('Auth check failed:', error);
          setIsTokenValid(false);
          setToken(null);
        }
      } else {
        setIsTokenValid(false);
      }
      setIsCheckingAuth(false);
    };

    checkAuthentication();
  }, []);

  const loadRealEstateObject = useCallback(async (id) => {
    try {
      const response = await apiCall(`${ENDPOINTS.REAL_ESTATE_API}/${id}`, {
        method: 'GET'
      }, navigate);
      
      if (response.ok) {
        const data = await response.json();
        setRealEstateObject(data);
      } else {
        setError('Failed to load real estate object');
        console.error('Failed to load real estate object');
      }
    } catch (error) {
      setError('Error: ' + error.message);
      console.error('Error:', error);
    }
  }, [navigate]);

  const handleLogout = async () => {
    try {
      await logout();
      setToken(null);
      setIsTokenValid(false);
      navigate('/');
    } catch (error) {
      console.error('Logout failed:', error);
      // Force logout even if API call fails
      setToken(null);
      setIsTokenValid(false);
      navigate('/');
    }
  };

  useEffect(() => {
    // Only extract ID from detail routes
    const path = window.location.pathname;
    const detailsMatch = path.match(/\/immobilien\/details\/(\d+)$/);
    
    if (detailsMatch && isTokenValid) {
        const id = detailsMatch[1]; // Extract the numeric ID
        loadRealEstateObject(id);
    }
  }, [isTokenValid, loadRealEstateObject]);

  useEffect(() => {
    if (token && token !== "undefined" && token !== "") {
      localStorage.setItem('token', token);
      setIsTokenValid(true);
    } else {
      localStorage.removeItem('token');
      setIsTokenValid(false);
    }
  }, [token]);

  // Show loading while checking authentication
  if (isCheckingAuth) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100">
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  // Show login if not authenticated
  if (!isTokenValid) {
    return (
      <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
        {error && <ErrorPanel message={error} onClose={() => setError(null)} />}
        <Sidebar />
        <div className="content-wrapper">
          <section className="content">
            <Login setToken={setToken} setError={setError} />
          </section>
        </div>
      </div>
    );
  }

  return (
      <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
        {error && <ErrorPanel message={error} onClose={() => setError(null)} />}
        <Header onLogout={handleLogout} />
        <Sidebar />
        <div className="content-wrapper">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/immobilien" element={<RealEstateOverview />} />
            <Route path="/real-estate" element={<RealEstateOverview />} />
            <Route path="/real-estate/form" element={<RealEstateForm />} />
            <Route path="/immobilien/details/:id" element={<RealEstateDetails realEstateObject={realEstateObject} />} />
            <Route path="/tennants" element={<TennantsOverview />} />
            <Route path="/companies" element={<CompaniesOverview />} />
            <Route path="/tickets" element={<TicketsOverview />} />
            <Route path="/tickets/create" element={<TicketCreate />} />
          </Routes>
        </div>
      </div>
  );
};

// ErrorPanel component
const ErrorPanel = ({ message, onClose }) => {
  return (
    <div style={{
      position: 'fixed',
      top: '20px',
      right: '20px',
      backgroundColor: '#ff4444',
      color: 'white',
      padding: '15px',
      borderRadius: '5px',
      zIndex: 1000,
      maxWidth: '400px',
      boxShadow: '0 4px 8px rgba(0,0,0,0.2)'
    }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <span>{message}</span>
        <button
          onClick={onClose}
          style={{
            backgroundColor: 'transparent',
            border: 'none',
            color: 'white',
            cursor: 'pointer',
            fontSize: '16px',
            marginLeft: '10px'
          }}
        >
          ×
        </button>
      </div>
    </div>
  );
};

ErrorPanel.propTypes = {
  message: PropTypes.string.isRequired,
  onClose: PropTypes.func.isRequired,
};

export default App;
