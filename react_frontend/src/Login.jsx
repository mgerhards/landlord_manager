import { useState } from 'react';
import PropTypes from 'prop-types';

const Login = ({ setToken, setError }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                credentials: 'include', // Include credentials to handle cookies
                body: new URLSearchParams({
                    username,
                    password,
                }),
            });
            
            if (response.ok) {
                const token = await response.text(); // Assuming the token is returned as plain text
                setToken(token);
            } else {
                setError('Login failed. Please check your credentials.');
            }
        } catch (error) {
            setError('Network error. Please try again.');
            console.error('Login error:', error);
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
                        <div>
                            <label className="form-label">Username:</label>
                            <input className="form-control" type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                        </div>
                        <div>
                            <label className="form-label">Password:</label>
                            <input className="form-control" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                        </div>
                    </div>
                    <div className='card-footer'>
                        <button className="btn btn-primary" type="submit">Login</button>
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