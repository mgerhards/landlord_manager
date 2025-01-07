import React, { useEffect, useState } from "react";
import VizPage from "./components/VizPage";
import { setToken, clearToken, getToken } from "./utils/token";

function App() {
  const [token, setAuthToken] = useState(getToken());

  const generateCodeVerifier = () => {
    const array = new Uint32Array(56 / 2);
    window.crypto.getRandomValues(array);
    return Array.from(array, dec => ('0' + dec.toString(16)).substr(-2)).join('');
  };

  const sha256 = async (plain) => {
    const encoder = new TextEncoder();
    const data = encoder.encode(plain);
    return window.crypto.subtle.digest('SHA-256', data);
  };

  const base64urlencode = (a) => {
    let str = '';
    const bytes = new Uint8Array(a);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      str += String.fromCharCode(bytes[i]);
    }
    return btoa(str).replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
  };

  const generateCodeChallenge = async (verifier) => {
    const hashed = await sha256(verifier);
    return base64urlencode(hashed);
  };

  const logout = () => {
    clearToken();
    setAuthToken(null);
  };

  const authenticate = async () => {
    const code = new URLSearchParams(window.location.search).get('code');
    if (code) {
      const codeVerifier = localStorage.getItem('code_verifier');
      const response = await fetch('http://localhost:8080/oauth2/token', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          grant_type: 'authorization_code',
          code,
          redirect_uri: 'http://localhost:5173/',
          client_id: 'client',
          code_verifier: codeVerifier,
        }),
      });

      if (response.ok) {
        const { access_token } = await response.json();
        setToken(access_token);
        setAuthToken(access_token);
      } else {
        console.error('Token exchange failed');
      }
    } else if (!token) {
      const codeVerifier = generateCodeVerifier();
      const codeChallenge = await generateCodeChallenge(codeVerifier);
      localStorage.setItem('code_verifier', codeVerifier);

      const authUrl = `http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&redirect_uri=http://localhost:5173/&scope=openid&code_challenge=${codeChallenge}&code_challenge_method=S256`;
      window.location.href = authUrl;
    }
  };

  useEffect(() => {
    authenticate();
  }, []);

  return (
    <div>
      <h1>React OAuth Client</h1>
      {token ? (
        <>
          <button onClick={logout}>Logout</button>
          <VizPage />
        </>
      ) : (
        <p>Redirecting to login...</p>
      )}
    </div>
  );
}

export default App;
