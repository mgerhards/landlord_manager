import React from 'react';
import { useEffect, useState } from 'react';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import "./App.css"
import { Routes, Route } from 'react-router-dom';
import RealEstateOverview  from './components/RealEstate/Overview.jsx';
import Login from './Login';


const Home = () => <h3>Home</h3>;


const App = () => {
  const [token, setToken] = useState(null);
  if (!token) {
    return <Login setToken={setToken} />;
  }

  useEffect(() => {
    // Initialize AdminLTE components if necessary
    import('admin-lte');
  }, []);

  return (
    <div className="app-wrapper sidebar-expand-lg bg-body-tertiary sidebar-open">
      <Header/>
      <Sidebar />
      <div className="content-wrapper">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/immobilien" element={<RealEstateOverview />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;