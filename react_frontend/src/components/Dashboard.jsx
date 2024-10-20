// src/components/Dashboard.jsx

import React from 'react';

import Layout from './Layout';

const Dashboard = () => {
    return (
        <Layout>
        <div className="card">
            <div className="card-header">
            <h3 className="card-title">Welcome to the Dashboard</h3>
            </div>
            <div className="card-body">
            This is your main content area where you can add charts, tables, and other dashboard elements.
            </div>
        </div>
        </Layout>
    );
    };


export default Dashboard;