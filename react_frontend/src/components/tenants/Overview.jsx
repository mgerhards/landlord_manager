import { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';
import { apiCall } from '../../config/auth';

const TenantsOverview = () => {
    const [tenants, setTenants] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTenants = async () => {
            try {
                setLoading(true);
                const response = await apiCall(ENDPOINTS.TENANTS_BY_LANDLORD, {
                    method: 'GET',
                    headers: { 'Accept': 'application/json' }
                });
                
                if (response.ok) {
                    const data = await response.json();
                    setTenants(data);
                } else {
                    setError('Failed to fetch tenants');
                }
            } catch (error) {
                console.error('Error fetching tenants:', error);
                setError('Error fetching tenants: ' + error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchTenants();
    }, []);

    if (loading) {
        return (
            <div className="container">
                <h2>Mieter</h2>
                <div className="text-center">
                    <div className="spinner-border" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container">
                <h2>Mieter</h2>
                <div className="alert alert-danger" role="alert">
                    {error}
                </div>
            </div>
        );
    }

    return (
        <div className="container">
            <h2>Mieter</h2>
            <div className="table-responsive">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Telefonnummer</th>
                            <th>Alternative Telefonnummer</th>
                            <th>Arbeitsplatz Kontakt</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tenants.length === 0 ? (
                            <tr>
                                <td colSpan="4" className="text-center">
                                    Keine Mieter gefunden
                                </td>
                            </tr>
                        ) : (
                            tenants.map((tenant) => (
                                <tr key={tenant.id}>
                                    <td>{tenant.name || 'N/A'}</td>
                                    <td>{tenant.phoneNumber || 'N/A'}</td>
                                    <td>{tenant.alternativePhoneNumber || 'N/A'}</td>
                                    <td>{tenant.employerContact || 'N/A'}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TenantsOverview;
