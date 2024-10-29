// src/components/Dashboard.jsx

import React, { useState, useEffect } from 'react';

const RealEstateOverview = () => {
    const [realEstateObjects, setRealEstateObjects] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/realEstateObjects')
            .then(response => response.json())
            .then(data => setRealEstateObjects(data._embedded.realEstateObjects))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className="card">
            <div className="card-header">
                <h3 className="card-title">Immobilien</h3>
            </div>
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
    );
};

export default RealEstateOverview;