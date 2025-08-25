import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ENDPOINTS } from '../../config/api';
import { apiCall } from '../../config/auth';

const TicketCreate = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        description: '',
        tenantId: '',
        assetId: '',
        companyId: ''
    });
    const [tenants, setTenants] = useState([]);
    const [assets, setAssets] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    // Fetch tenants, assets, and companies for dropdown selection
    useEffect(() => {
        const fetchData = async () => {
            try {
                // Fetch tenants
                const tenantsResponse = await apiCall(ENDPOINTS.TENANT_ENTITIES, {
                    method: 'GET'
                }, navigate);
                if (tenantsResponse.ok) {
                    const tenantsData = await tenantsResponse.json();
                    setTenants(tenantsData._embedded?.tenants || []);
                }

                // Fetch real estate objects
                const assetsResponse = await apiCall(ENDPOINTS.REAL_ESTATE, {
                    method: 'GET'
                }, navigate);
                if (assetsResponse.ok) {
                    const assetsData = await assetsResponse.json();
                    setAssets(assetsData._embedded?.realEstateObjects || []);
                }

                // Fetch companies
                const companiesResponse = await apiCall(ENDPOINTS.COMPANIES, {
                    method: 'GET'
                }, navigate);
                if (companiesResponse.ok) {
                    const companiesData = await companiesResponse.json();
                    setCompanies(companiesData || []);
                }

                // Fetch cheapest emergency company and set as default
                const cheapestResponse = await apiCall(ENDPOINTS.COMPANIES_CHEAPEST_EMERGENCY, {
                    method: 'GET'
                }, navigate);
                if (cheapestResponse.ok) {
                    const cheapestCompany = await cheapestResponse.json();
                    if (cheapestCompany && cheapestCompany.id) {
                        setFormData(prev => ({
                            ...prev,
                            companyId: cheapestCompany.id.toString()
                        }));
                    }
                }
            } catch (error) {
                console.error('Error fetching data:', error);
                setError('Failed to load required data');
            }
        };

        fetchData();
    }, [navigate]);

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
            // Prepare ticket data for backend
            const ticketData = {
                description: formData.description,
                tenant: formData.tenantId ? { id: formData.tenantId } : null,
                asset: formData.assetId ? { id: formData.assetId } : null,
                craftsmanFirm: formData.companyId ? { id: formData.companyId } : null
            };

            const response = await apiCall(ENDPOINTS.TICKETS, {
                method: 'POST',
                body: JSON.stringify(ticketData)
            }, navigate);

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

                                <div className="form-group mb-3">
                                    <label htmlFor="companyId" className="form-label">
                                        Assigned Company
                                    </label>
                                    <select
                                        id="companyId"
                                        name="companyId"
                                        className="form-control"
                                        value={formData.companyId}
                                        onChange={handleInputChange}
                                    >
                                        <option value="">Select a company (optional)</option>
                                        {companies.map(company => (
                                            <option key={company.id} value={company.id}>
                                                {company.companyName} - {company.emergencyHourlyRate ? `€${company.emergencyHourlyRate}/h emergency` : 'No emergency service'}
                                            </option>
                                        ))}
                                    </select>
                                    <small className="form-text text-muted">
                                        The cheapest emergency service provider is pre-selected by default.
                                    </small>
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