// Base URL for API endpoints
export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

// API endpoints matching Spring Data REST
export const ENDPOINTS = {
    REAL_ESTATE: `${API_BASE_URL}/realEstateObjects`,
    TICKETS: `${API_BASE_URL}/ticket`,
    TENANTS: `${API_BASE_URL}/tenants`,
    LANDLORDS: `${API_BASE_URL}/landlord`,
    CRAFTSMAN_FIRMS: `${API_BASE_URL}/craftsmanFirm`,
    CONTRACTS: `${API_BASE_URL}/contract`,
    FRAMEWORK_CONTRACTS: `${API_BASE_URL}/frameworkContracts`,
    TICKET_COMMENTS: `${API_BASE_URL}/ticketComments`,
    USER_ACCOUNTS: `${API_BASE_URL}/userAccounts`,
    PROFILE: `${API_BASE_URL}/profile`,
    // Authentication endpoints (custom controller)
    AUTH: `${API_BASE_URL}/api/auth`,
    // Add other endpoints here as needed
}; 