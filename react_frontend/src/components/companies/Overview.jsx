import React, { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';

const CompaniesOverview = () => {
    const [companies, setCompanies] = useState([]); 

    useEffect(() => {
        fetch(ENDPOINTS.COMPANIES, {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => setCompanies(data._embedded.craftsmanFirms))
        .catch(error => console.error('Error fetching tennants:', error));;
    });     

    return (
        <div className="container">
            <h2>Firmen</h2>
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
                        {companies.map((company) => (
                            <tr key={company.id}>
                                <td>{company.companyName}</td>
                                <td>{company.contactDetails}</td>
                                
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};  

export default CompaniesOverview;
