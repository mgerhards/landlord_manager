import { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';
import { apiCall } from '../../config/auth';

const TenantsOverview = () => {
    const [tenants, setTenants] = useState([]);

    useEffect(() => {
        // In the useEffect:
        apiCall(ENDPOINTS.TENANT_ENTITIES,
            { method: 'GET', headers: { 'Accept': 'application/json' } })
            .then(data => setTenants(data._embedded.tenants))
            .catch(error => console.error('Error fetching tenants:', error));
    }, []);

    return (
        <div className="container">
            <h2>Mieter</h2>
            <div className="table-responsive">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Telefon</th>
                            <th>Adresse</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tenants.map((tenant) => (
                            <tr key={tenant.id}>
                                <td>{tenant.name}</td>
                                <td></td>
                                <td>{tenant.alternativePhoneNumber}</td>
                                <td>{tenant.employerContact}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TenantsOverview;
