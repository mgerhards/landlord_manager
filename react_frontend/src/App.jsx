import React from 'react';
import { useEffect } from 'react';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import "./App.css"
import { Routes, Route } from 'react-router-dom';


const Home = () => <h1>Home</h1>;
const Immobilien = () => <h1>Immobilien</h1>;

const App = () => {
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
          <Route path="/immobilien" element={<Immobilien />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;