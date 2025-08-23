// Base URL for API endpoints
export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

// API endpoints matching your backend controllers
export const ENDPOINTS = {
    // Custom controller endpoints (protected)
    REAL_ESTATE_API: `${API_BASE_URL}/api/real-estate`,
    TICKETS: `${API_BASE_URL}/api/tickets`,
    TENANTS: `${API_BASE_URL}/api/tenants`,
    TENANTS_BY_LANDLORD: `${API_BASE_URL}/api/tenants/by-landlord`,
    LANDLORDS: `${API_BASE_URL}/api/landlords`,
    CRAFTSMAN_FIRMS: `${API_BASE_URL}/api/craftsman-firms`,
    CONTRACTS: `${API_BASE_URL}/api/contracts`,
    PROPERTY_TYPES: `${API_BASE_URL}/api/property-types`,
    CONTRACT_STATUSES: `${API_BASE_URL}/api/contract-statuses`,
    
    // Spring Data REST endpoints (if still using some)
    REAL_ESTATE: `${API_BASE_URL}/realEstateObjects`,
    TICKET_ENTITIES: `${API_BASE_URL}/ticket`,
    TENANT_ENTITIES: `${API_BASE_URL}/tenants`,
    LANDLORD_ENTITIES: `${API_BASE_URL}/landlord`,
    CRAFTSMAN_FIRM_ENTITIES: `${API_BASE_URL}/craftsmanFirm`,
    CONTRACT_ENTITIES: `${API_BASE_URL}/contract`,
    FRAMEWORK_CONTRACTS: `${API_BASE_URL}/frameworkContracts`,
    TICKET_COMMENTS: `${API_BASE_URL}/ticketComments`,
    USER_ACCOUNTS: `${API_BASE_URL}/userAccounts`,
    PROFILE: `${API_BASE_URL}/profile`,
    
    // Authentication endpoints (custom controller)
    AUTH: `${API_BASE_URL}/api/auth`,
    AUTH_LOGIN: `${API_BASE_URL}/api/auth/login`,
    AUTH_LOGOUT: `${API_BASE_URL}/api/auth/logout`,
    AUTH_VERIFY: `${API_BASE_URL}/api/auth/verify`,
    
    // Test endpoints
    TEST_PROTECTED: `${API_BASE_URL}/api/test/protected`,
    TEST_USER_INFO: `${API_BASE_URL}/api/test/user-info`,
}; 