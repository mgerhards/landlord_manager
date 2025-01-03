import { useState } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

const Login = ({ setToken }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await fetch('http://localhost:8080/oauth2/token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Basic ' + btoa('client:secret')
            },           
            body: new URLSearchParams({
                grant_type: 'password',
                username,
                password,
            }),
        });

        if (response.ok) {
            const token = await response.text(); // Assuming the token is returned as plain text
            setToken(token);
        } else {
            console.error('Login failed');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
          
            {error && <div id="toastsContainerTopRight" className="toasts-top-right fixed">
                        <div className="toast bg-danger fade show" role="alert" aria-live="assertive" aria-atomic="true">
                            <div className="toast-header">
                                <strong className="mr-auto">Error</strong><small>Login failed</small><button data-dismiss="toast" type="button" className="ml-2 mb-1 close" aria-label="Close"><span aria-hidden="true">×</span></button>
                            </div>
                            <div className="toast-body">{error}</div>
                        </div>
                        <div className="toast bg-success fade show" role="alert" aria-live="assertive" aria-atomic="true"></div>
                    </div>}

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

// Login.propTypes = {
//     setIsAuthenticated: PropTypes.func.isRequired,
// };

export default Login;