import React, { useState } from "react";
import Login from "./Login";
import VizPage from "./components/VizPage";
import { setToken, clearToken, getToken } from "./utils/token";

function App() {
  const [token, setAuthToken] = useState(getToken());

  const logout = () => {
    clearToken();
    setAuthToken(null);
  };

  return (
    <div>
      <h1>React OAuth Client</h1>
      {token ? (
        <>
          <button onClick={logout}>Logout</button>
          <VizPage />
        </>
      ) : (
        <Login setToken={(token) => {
          setToken(token);
          setAuthToken(token);
        }} />
      )}
    </div>
  );
}

export default App;