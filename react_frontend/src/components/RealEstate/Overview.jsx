// src/components/Dashboard.jsx

import React, { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../config/api';

const RealEstateOverview = () => {
    const [realEstateObjects, setRealEstateObjects] = useState([]);

    useEffect(() => {
        fetch(ENDPOINTS.REAL_ESTATE, {
            credentials: 'include',
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
            <div className="row">
                {realEstateObjects.map((obj) => {
                    return (
                        <div className="col-md-4 mb-4" key={obj.id}>
                            <div className="card">
                                <div className="card-body">
                                    <iframe
                                        width="100%"
                                        height="200"
                                        frameBorder="0" 
                                        style={{ border: 0 }}
                                        src={`https://www.openstreetmap.org/export/embed.html?bbox=${obj.longitude-0.001}%2C${obj.latitude-0.001}%2C${obj.longitude+0.001}%2C${obj.latitude+0.001}&layer=mapnik&marker=${obj.latitude}%2C${obj.longitude}`}
                                        allowFullScreen
                                    ></iframe>
                                    <h4 className="card-title mt-3 w-100">{obj.address}</h4>
                                    <div className="card-text">
                                        <p><strong>Größe:</strong> {obj.size} m²</p>
                                        <p><strong>Anzahl Zimmer:</strong> {obj.numberOfRooms}</p>
                                        <p><strong>Beschreibung:</strong> {obj.description}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default RealEstateOverview;