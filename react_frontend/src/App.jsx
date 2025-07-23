import { useEffect, useState, useCallback } from 'react';
import PropTypes from 'prop-types';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import "./App.css"
import { Routes, Route } from 'react-router-dom';
import RealEstateOverview from './components/RealEstate/Overview.jsx';
import RealEstateForm from './components/RealEstate/Form.jsx';
import TennantsOverview from './components/tennants/Overview';
import CompaniesOverview from './components/companies/Overview';
import TicketsOverview from './components/tickets/Overview';
import TicketCreate from './components/tickets/TicketCreate';
import RealEstateDetails from './components/RealEstate/Details';
import Login from './Login';

const Home = () => <h3>Home</h3>;

const App = () => {
  useEffect(() => {
    // Initialize AdminLTE components if necessary
    import('admin-lte');
  }, []);

  const [token, setToken] = useState(localStorage.getItem('token'));
  const [realEstateObject, setRealEstateObject] = useState(null);
  const [error, setError] = useState(null);

  const loadRealEstateObject = useCallback(async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/realestate/${id}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
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
  }, [token, setError]);

  useEffect(() => {
    const id = window.location.pathname.split('/').pop();
    if (id) {
      loadRealEstateObject(id);
    }
  }, [token]);

  useEffect(() => {
    if (token) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem('token');
    }
  }, [token, loadRealEstateObject]);

  if (!token && token !== "undefined" && token !=="") {
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
        <Header />
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