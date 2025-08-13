import { ENDPOINTS } from './api.js';
import {jwtDecode} from 'jwt-decode';
 
/**
 * Get the JWT token from localStorage
 */
export const getToken = () => {
    return localStorage.getItem('token');
};

/**
 * Remove the JWT token from localStorage
 */
export const removeToken = () => {
    localStorage.removeItem('token');
};

/**
 * Check if user is authenticated (has a token)
 */
export const isAuthenticated = () => {
    const token = getToken();
    return token && token !== 'undefined' && token !== '';
};

/**
 * Create headers for authenticated requests
 */
export const createAuthHeaders = (additionalHeaders = {}) => {
    const token = getToken();
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        ...additionalHeaders
    };
    
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }else{
        console.log('No token found, proceeding without Authorization header');
    }
    
    return headers;
};

/**
 * Handle API response errors (especially 401 Unauthorized)
 */
export const handleApiError = async (response, navigate = null) => {
    if (response.status === 401) {
        // Token is invalid or expired
        removeToken();
        if (navigate) {
            navigate('/login');
        } else {
            // Reload the page to show login form
            window.location.reload();
        }
        throw new Error('Authentication required. Please log in again.');
    }
    
    if (!response.ok) {
        const errorText = await response.text().catch(() => 'Unknown error');
        throw new Error(`API Error (${response.status}): ${errorText}`);
    }
    
    return response;
};

/**
 * Make an authenticated API call
 */
export const apiCall = async (url, options = {}, navigate = null) => {
    const defaultOptions = {
        headers: createAuthHeaders(options.headers),
        credentials: 'include',
        ...options
    };
    
    try {
        const response = await fetch(url, defaultOptions);
        await handleApiError(response, navigate);
        return response;
    } catch (error) {
        console.error('API call failed:', error);
        throw error;
    }
};

/**
 * Verify if the current token is still valid
 */
export const verifyToken = async () => {
    if (!isAuthenticated()) {
        return false;
    }
    
    try {
        const response = await apiCall(ENDPOINTS.AUTH_VERIFY, { method: 'GET' });
        return response.ok;
    } catch (error) {
        console.error('Token verification failed:', error);
        removeToken();
        return false;
    }
};

/**
 * Login with username and password
 */
export const login = async (username, password) => {
    const response = await fetch(ENDPOINTS.AUTH_LOGIN, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        credentials: 'include',
        body: new URLSearchParams({
            username,
            password,
        }),
    });
    
    if (!response.ok) {
        const errorText = await response.text().catch(() => 'Login failed');
        throw new Error(errorText);
    }
    
    const token = await response.text();
    localStorage.setItem('token', token);
    return token;
};

/**
 * Logout the current user
 */
export const logout = async () => {
    try {
        await apiCall(ENDPOINTS.AUTH_LOGOUT, { method: 'POST' });
    } catch (error) {
        console.error('Logout API call failed:', error);
        // Continue with local logout even if API call fails
    } finally {
        removeToken();
    }
};
 /**
  * Extracts the user id from JWT
  *
  * @returns {string|null} The user id or null if not found
  */
export const getUserIdFromToken = () => {
  const token = getToken();
  if (!token) return null;
  try {
    const decoded = jwtDecode(token);
    return decoded.userId || null;
  } catch (e) {
    return null;
  }
};
