import React, { useEffect, useState } from "react";
import apiClient from "../utils/vizApiClient";

function VizPage() {
  const [data, setData] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiClient.get("http://localhost:8000/secure-endpoint");
        setData(response.data);
      } catch (err) {
        setError("Failed to fetch data. Ensure you are logged in.");
      }
    };
    fetchData();
  }, []);

  return (
    <div>
      <h2>Secure Page</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {data ? <pre>{JSON.stringify(data, null, 2)}</pre> : <p>Loading...</p>}
    </div>
  );
}

export default VizPage;