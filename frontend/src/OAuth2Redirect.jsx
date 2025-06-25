import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";

const OAuth2Redirect = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [error, setError] = useState(null);

    useEffect(() => {
        const urlParams = new URLSearchParams(location.search);
        const token = urlParams.get("token");
        const username = urlParams.get("username");
        const role = urlParams.get("role");
        const errorParam = urlParams.get("error");

        console.log("🔐 OAuth2 Redirect Params:", { token, username, role, error: errorParam });

        if (errorParam) {
            if (errorParam === "not_registered") {
                setError("Your account is not registered. Please sign up or contact support.");
            } else if (errorParam === "invalid_email") {
                setError("Invalid email received from Google. Please try again.");
            } else if (errorParam === "invalid_user_data") {
                setError("Invalid user data. Please contact support.");
            } else if (errorParam === "server_error") {
                setError("Server error occurred. Please try again later.");
            } else {
                setError("An unknown error occurred. Please try again.");
            }
            return;
        }

        if (token && username && role) {
            localStorage.setItem("token", token);
            localStorage.setItem("username", username);
            localStorage.setItem("role", role);

            if (role === "ROLE_EMPLOYEE") {
                navigate("/employee-dashboard");
            } else {
                navigate("/dashboard");
            }
        } else {
            console.warn("❌ Missing OAuth2 params — redirecting to login");
            navigate("/login?error=not_registered");
        }
    }, [navigate, location]);

    if (error) {
        return <p className="text-center mt-10 text-red-500">{error}</p>;
    }

    return <p className="text-center mt-10">Logging you in...</p>;
};

export default OAuth2Redirect;