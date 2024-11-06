import { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';

const TennantsOverview = () => {
    const [tennants, setTennants] = useState([]);

    useEffect(() => {
        fetch(ENDPOINTS.TENNANTS, {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => setTennants(data._embedded.tenants))
        .catch(error => console.error('Error fetching tennants:', error));
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
                        {tennants.map((tennant) => (
                            <tr key={tennant.id}>
                                <td>{tennant.name}</td>
                                <td>{tennant.email}</td>
                                <td>{tennant.phone}</td>
                                <td>{tennant.address}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TennantsOverview;
