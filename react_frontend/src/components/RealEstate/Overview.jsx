// src/components/Dashboard.jsx

import React, { useState, useEffect } from 'react';

const RealEstateOverview = () => {
    const [realEstateObjects, setRealEstateObjects] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/realEstateObjects',{
                credentials: 'include',  // Add this line to send cookies
                headers: {
                    'Accept': 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => setRealEstateObjects(data._embedded.realEstateObjects))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className="container justify-content-center align-items-center vh-100">
            <h2>Immobilien</h2>           
            <div className="card">               
                <div className="card-body">
                    Hier können Sie Ihre Immobilien verwalten.
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Adress</th>
                                <th>Size</th>
                                <th>Number of Rooms</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            {realEstateObjects.map((obj) => (
                                <tr key={obj.id}>
                                    <td>{obj.adress}</td>
                                    <td>{obj.size}</td>
                                    <td>{obj.numberOfRooms}</td>
                                    <td>{obj.description}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default RealEstateOverview;