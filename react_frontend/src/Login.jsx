import { useState } from 'react';
import PropTypes from 'prop-types';
import { login } from './config/auth.js';

const Login = ({ setToken, setError }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        
        try {
            const token = await login(username, password);
            setToken(token);
        } catch (error) {
            setError(error.message || 'Login failed. Please check your credentials.');
            console.error('Login error:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="login-wrapper container d-flex justify-content-center align-items-center vh-100">
            
                <div className='card card-primary card-outline mb-4 w-50'>
                    <div className='card-header'>
                        <h3 className='card-title'>Login</h3>
                    </div>
                    <div className='card-body'>
                        <div className="form-group mb-3">
                            <label className="form-label">Username:</label>
                            <input 
                                className="form-control" 
                                type="text" 
                                value={username} 
                                onChange={(e) => setUsername(e.target.value)}
                                disabled={loading}
                                required 
                            />
                        </div>
                        <div className="form-group mb-3">
                            <label className="form-label">Password:</label>
                            <input 
                                className="form-control" 
                                type="password" 
                                value={password} 
                                onChange={(e) => setPassword(e.target.value)}
                                disabled={loading}
                                required 
                            />
                        </div>
                    </div>
                    <div className='card-footer'>
                        <button 
                            className="btn btn-primary" 
                            type="submit"
                            disabled={loading}
                        >
                            {loading ? 'Logging in...' : 'Login'}
                        </button>
                    </div>
                </div>
            </div>
        </form>
        
    );
};

Login.propTypes = {
    setToken: PropTypes.func.isRequired,
    setError: PropTypes.func.isRequired,
};

export default Login;