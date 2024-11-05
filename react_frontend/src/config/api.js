// Base URL for API endpoints
export const API_BASE_URL =  import.meta.env.VITE_API_URL || 'http://localhost:8080';

// API endpoints
export const ENDPOINTS = {
    REAL_ESTATE: `${API_BASE_URL}/realEstateObjects`,
    TENNANTS: `${API_BASE_URL}/tennants`,
    COMPANIES: `${API_BASE_URL}/companies`,
    TICKETS: `${API_BASE_URL}/tickets`,
    // Add other endpoints here as needed
}; 