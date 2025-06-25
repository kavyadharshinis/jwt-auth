// frontend/src/utils/axios.js
import axios from "axios";

// Create shared axios instance
const instance = axios.create({
    baseURL: "http://localhost:8080", // backend API
});

// Attach JWT token to all requests automatically
instance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default instance;
