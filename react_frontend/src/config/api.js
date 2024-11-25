// Base URL for API endpoints
export const API_BASE_URL =  'http://localhost:8080';//import.meta.env.VITE_API_URL || ;

// API endpoints
export const ENDPOINTS = {
    REAL_ESTATE: `${API_BASE_URL}/realEstateObjects`,
    TENNANTS: `${API_BASE_URL}/tenants`,
    COMPANIES: `${API_BASE_URL}/craftsmanFirm`,
    TICKETS: `${API_BASE_URL}/ticket`,
    // Add other endpoints here as needed
}; 