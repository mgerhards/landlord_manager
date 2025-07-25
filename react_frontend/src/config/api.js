// Base URL for API endpoints
export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// API endpoints
export const ENDPOINTS = {
    REAL_ESTATE: `${API_BASE_URL}/realEstateObjects`,
    REAL_ESTATE_API: `${API_BASE_URL}/api/real-estate`,
    TENNANTS: `${API_BASE_URL}/tenants`,
    COMPANIES: `${API_BASE_URL}/craftsmanFirm`,
    TICKETS: `${API_BASE_URL}/ticket`,
    // Add other endpoints here as needed
}; 