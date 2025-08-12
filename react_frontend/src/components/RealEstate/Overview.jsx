// src/components/Dashboard.jsx

import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { ENDPOINTS } from '../../config/api';
import OpenDetailsButton from './OpenDetailsButton';
import { apiCall, getUserIdFromToken} from '../../config/auth';



const RealEstateOverview = () => {
    const [realEstateObjects, setRealEstateObjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const location = useLocation();
    const userId = getUserIdFromToken();

    const fetchRealEstateObjects = () => {
        setLoading(true);

        apiCall(
            ENDPOINTS.REAL_ESTATE_API + "/landlord/" + userId,
            { method: 'GET', headers: { 'Accept': 'application/json' } }
        )
        .then(response => response.json())
        .then(data => setRealEstateObjects(data._embedded.realEstateObjects))
        .catch(error => console.error('Error fetching data:', error))
        .finally(() => setLoading(false));
    };

    useEffect(() => {
        setLoading(true);

        apiCall(
            ENDPOINTS.REAL_ESTATE_API + "/landlord/" + userId,
            { method: 'GET', headers: { 'Accept': 'application/json' } }
        )
        .then(response => response.json())
        .then(data => setRealEstateObjects(data._embedded.realEstateObjects))
        .catch(error => console.error('Error fetching data:', error))
        .finally(() => setLoading(false));
    }, [userId]);

    const handleEdit = (obj) => {
        navigate('/real-estate/form', { 
            state: { 
                realEstateObject: obj, 
                isEdit: true 
            } 
        });
    };

    const handleDelete = async (obj) => {
        if (window.confirm(`Are you sure you want to delete the property at ${obj.address}?`)) {
            try {
                const response = await apiCall(
                    `${ENDPOINTS.REAL_ESTATE_API}/${obj.id}`,
                    { method: 'DELETE' }
                );

                if (response.ok) {
                    // Refresh the list
                    fetchRealEstateObjects();
                } else {
                    alert('Failed to delete property');
                }
            } catch (error) {
                console.error('Error deleting property:', error);
                alert('Failed to delete property');
            }
        }
    };

    const handleCreateNew = () => {
        navigate('/real-estate/form', { 
            state: { 
                realEstateObject: null, 
                isEdit: false 
            } 
        });
    };

    return (
        <div className="container justify-content-center align-items-center vh-100">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Immobilien</h2>
                <button 
                    className="btn btn-primary"
                    onClick={handleCreateNew}
                >
                    <i className="fas fa-plus"></i> Neue Immobilie
                </button>
            </div>

            {/* Success message */}
            {location.state?.message && (
                <div className="alert alert-success alert-dismissible fade show" role="alert">
                    {location.state.message}
                    <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            )}

            {loading ? (
                <div className="text-center">
                    <div className="spinner-border" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            ) : (
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
                                            <div className="d-flex gap-2">
                                                <OpenDetailsButton obj={obj} />
                                                <button 
                                                    className="btn btn-outline-primary btn-sm"
                                                    onClick={() => handleEdit(obj)}
                                                >
                                                    <i className="fas fa-edit"></i> Edit
                                                </button>
                                                <button 
                                                    className="btn btn-outline-danger btn-sm"
                                                    onClick={() => handleDelete(obj)}
                                                >
                                                    <i className="fas fa-trash"></i> Delete
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        );
                    })}
                </div>
            )}
        </div>
    );
};

export default RealEstateOverview;