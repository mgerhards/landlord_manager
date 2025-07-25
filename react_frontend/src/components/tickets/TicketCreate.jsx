import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ENDPOINTS } from '../../config/api';

const TicketCreate = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        description: '',
        tenantId: '',
        assetId: ''
    });
    const [tenants, setTenants] = useState([]);
    const [assets, setAssets] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    // Fetch tenants and assets for dropdown selection
    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem('token');
                const headers = {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${token}`
                };

                // Fetch tenants
                const tenantsResponse = await fetch(ENDPOINTS.TENNANTS, {
                    headers,
                    credentials: 'include'
                });
                if (tenantsResponse.ok) {
                    const tenantsData = await tenantsResponse.json();
                    setTenants(tenantsData._embedded?.tenants || []);
                }

                // Fetch real estate objects
                const assetsResponse = await fetch(ENDPOINTS.REAL_ESTATE, {
                    headers,
                    credentials: 'include'
                });
                if (assetsResponse.ok) {
                    const assetsData = await assetsResponse.json();
                    setAssets(assetsData._embedded?.realEstateObjects || []);
                }
            } catch (error) {
                console.error('Error fetching data:', error);
                setError('Failed to load required data');
            }
        };

        fetchData();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const token = localStorage.getItem('token');
            
            // Prepare ticket data for backend
            const ticketData = {
                description: formData.description,
                tenant: formData.tenantId ? { id: formData.tenantId } : null,
                asset: formData.assetId ? { id: formData.assetId } : null
            };

            const response = await fetch(`${ENDPOINTS.TICKETS}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                credentials: 'include',
                body: JSON.stringify(ticketData)
            });

            if (response.ok) {
                // Success - redirect to tickets overview
                navigate('/tickets');
            } else {
                const errorText = await response.text();
                setError(`Failed to create ticket: ${errorText || 'Unknown error'}`);
            }
        } catch (error) {
            console.error('Error creating ticket:', error);
            setError('Failed to create ticket. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = () => {
        navigate('/tickets');
    };

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-md-8">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Create New Ticket</h3>
                        </div>
                        <form onSubmit={handleSubmit}>
                            <div className="card-body">
                                {error && (
                                    <div className="alert alert-danger">
                                        {error}
                                    </div>
                                )}
                                
                                <div className="form-group mb-3">
                                    <label htmlFor="description" className="form-label">
                                        Description <span className="text-danger">*</span>
                                    </label>
                                    <textarea
                                        id="description"
                                        name="description"
                                        className="form-control"
                                        rows="4"
                                        placeholder="Describe the issue or request..."
                                        value={formData.description}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="tenantId" className="form-label">
                                        Tenant <span className="text-danger">*</span>
                                    </label>
                                    <select
                                        id="tenantId"
                                        name="tenantId"
                                        className="form-control"
                                        value={formData.tenantId}
                                        onChange={handleInputChange}
                                        required
                                    >
                                        <option value="">Select a tenant</option>
                                        {tenants.map(tenant => (
                                            <option key={tenant.id} value={tenant.id}>
                                                {tenant.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>

                                <div className="form-group mb-3">
                                    <label htmlFor="assetId" className="form-label">
                                        Property <span className="text-danger">*</span>
                                    </label>
                                    <select
                                        id="assetId"
                                        name="assetId"
                                        className="form-control"
                                        value={formData.assetId}
                                        onChange={handleInputChange}
                                        required
                                    >
                                        <option value="">Select a property</option>
                                        {assets.map(asset => (
                                            <option key={asset.id} value={asset.id}>
                                                {asset.address}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="card-footer">
                                <button 
                                    type="submit" 
                                    className="btn btn-primary"
                                    disabled={loading}
                                >
                                    {loading ? 'Creating...' : 'Create Ticket'}
                                </button>
                                <button 
                                    type="button" 
                                    className="btn btn-secondary ms-2"
                                    onClick={handleCancel}
                                    disabled={loading}
                                >
                                    Cancel
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TicketCreate;