import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom"; // ✅ Updated
import { FcGoogle } from "react-icons/fc";
import { FaFacebookF } from "react-icons/fa";
import axios from "./utils/axios";
import bgImage from "./assets/register.jpg";

const Login = () => {
    const navigate = useNavigate();
    const location = useLocation(); // ✅

    const [formData, setFormData] = useState({
        username: "",
        password: "",
    });

    const [error, setError] = useState("");

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const errorParam = params.get("error");
        if (errorParam === "not_registered") {
            setError("You must register before using Google/Facebook login.");
        }
    }, [location.search]);


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post("/auth/login", formData);

            const { token, username, role } = res.data;
            localStorage.setItem("token", token);
            localStorage.setItem("username", username);
            localStorage.setItem("role", role);

            if (role === "ROLE_EMPLOYEE") {
                navigate("/employee-dashboard");
            } else {
                navigate("/dashboard");
            }
        } catch (err) {
            console.error("Login error:", err);
            setError("Invalid username or password");
        }
    };

    const handleGoogleLogin = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    const handleFacebookLogin = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/facebook";
    };

    return (
        <div className="flex min-h-screen font-sans">
            {/* Left Image */}
            <div className="w-1/2 relative flex items-center justify-center bg-[#fef6e4] p-10">
                <img
                    src={bgImage}
                    alt="Background"
                    className="absolute top-0 left-0 w-full h-full object-cover opacity-85"
                />
            </div>

            {/* Right Login Form */}
            <div className="w-1/2 flex flex-col justify-center items-center bg-white p-10 z-10">
                <h2 className="text-2xl font-bold text-center mb-2">Login</h2>
                <p className="text-gray-600 italic mb-6 text-center">
                    Unlock trusted gold loan services
                </p>

                <form onSubmit={handleSubmit} className="w-full max-w-md space-y-4">
                    <input
                        type="text"
                        name="username"
                        placeholder="Username or Email"
                        value={formData.username}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />
                    {error && <p className="text-red-600 text-sm text-center">{error}</p>}

                    <button
                        type="submit"
                        className="w-full bg-[#eba75d] text-white py-2 rounded font-semibold hover:bg-[#d88f42]"
                    >
                        Login
                    </button>
                </form>

                <p className="mt-4 text-sm text-center text-gray-600">OR</p>

                <div className="flex gap-4 mt-4">
                    <button
                        onClick={handleGoogleLogin}
                        className="flex items-center border border-gray-300 px-4 py-2 rounded shadow-sm hover:shadow-md"
                    >
                        <FcGoogle className="mr-2" /> Google
                    </button>
                    <button
                        onClick={handleFacebookLogin}
                        className="flex items-center border border-gray-300 px-4 py-2 rounded shadow-sm hover:shadow-md text-blue-600"
                    >
                        <FaFacebookF className="mr-2" /> Facebook
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Login;
