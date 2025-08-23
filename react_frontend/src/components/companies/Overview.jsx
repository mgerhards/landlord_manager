import React, { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';
import { apiCall } from '../../config/auth';

const CompaniesOverview = () => {
    const [companies, setCompanies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCompanies = async () => {
            try {
                setLoading(true);
                setError(null);
                const response = await apiCall(ENDPOINTS.COMPANIES, {
                    method: 'GET'
                });
                
                if (!response.ok) {
                    throw new Error('Failed to fetch companies');
                }
                
                const data = await response.json();
                setCompanies(data);
            } catch (error) {
                console.error('Error fetching companies:', error);
                setError('Failed to load companies. Please try again.');
            } finally {
                setLoading(false);
            }
        };

        fetchCompanies();
    }, []);

    if (loading) {
        return (
            <div className="container">
                <h2>Firmen</h2>
                <div className="text-center">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container">
                <h2>Firmen</h2>
                <div className="alert alert-danger" role="alert">
                    {error}
                </div>
            </div>
        );
    }

    return (
        <div className="container">
            <h2>Firmen</h2>
            {companies.length === 0 ? (
                <div className="alert alert-info" role="alert">
                    Keine Firmen gefunden.
                </div>
            ) : (
                <div className="table-responsive">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>E-Mail</th>
                                <th>Telefon</th>
                                <th>Adresse</th>
                                <th>Gewerke</th>
                                <th>Notdienst</th>
                            </tr>
                        </thead>
                        <tbody>
                            {companies.map((company) => (
                                <tr key={company.id}>
                                    <td>{company.companyName || 'N/A'}</td>
                                    <td>{company.email || 'N/A'}</td>
                                    <td>{company.phone || 'N/A'}</td>
                                    <td>
                                        {company.street && company.houseNumber && company.city ? 
                                            `${company.street} ${company.houseNumber}, ${company.postalCode} ${company.city}` : 
                                            'N/A'
                                        }
                                    </td>
                                    <td>
                                        {company.trades && company.trades.length > 0 ? 
                                            company.trades.join(', ') : 
                                            'N/A'
                                        }
                                    </td>
                                    <td>
                                        {company.isEmergencyServiceProvider ? 
                                            <span className="badge bg-success">Ja</span> : 
                                            <span className="badge bg-secondary">Nein</span>
                                        }
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};  

export default CompaniesOverview;
