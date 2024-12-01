import { useEffect, useState } from 'react';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import "./App.css"
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RealEstateOverview from './components/RealEstate/Overview.jsx';
import TennantsOverview from './components/tennants/Overview';
import CompaniesOverview from './components/companies/Overview';
import TicketsOverview from './components/tickets/Overview';
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

  const loadRealEstateObject = async (id) => {
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
        console.error('Failed to load real estate object');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

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
  }, [token]);

  if (!token && token !== "undefined" && token !=="") {
    return (
      <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
        <Sidebar />
        <div className="content-wrapper">
          <Login setToken={setToken} />
        </div>
      </div>
    );
  }

  return (
      <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
        <Header />
        <Sidebar />
        <div className="content-wrapper">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/immobilien" element={<RealEstateOverview />} />
            <Route path="/immobilien/details/:id" element={<RealEstateDetails realEstateObject={realEstateObject} />} />
            <Route path="/tennants" element={<TennantsOverview />} />
            <Route path="/companies" element={<CompaniesOverview />} />
            <Route path="/tickets" element={<TicketsOverview />} />
          </Routes>
        </div>
      </div>
  );
};

export default App;