import axios from "axios";
import { getToken } from "./token";

const vizApiClient = axios.create({
  baseURL: "http://localhost:8000",
});

vizApiClient.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default vizApiClient;