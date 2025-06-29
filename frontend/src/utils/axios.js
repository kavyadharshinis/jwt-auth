import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8080",
});

instance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");

        // Attach token unless it's explicitly for login
        if (token && !config.url.endsWith("/auth/login") && !config.url.endsWith("/employee/login")) {
            config.headers["Authorization"] = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);

export default instance;
