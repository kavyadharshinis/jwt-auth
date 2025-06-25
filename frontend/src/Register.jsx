import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import bgImage from "./assets/register.jpg";
import axios from "./utils/axios"; // ✅ using shared axios instance

const Register = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        username: "",
        email: "",
        phone: "",
        dob: "",
        gender: "",
        password: "",
        confirmPassword: "",
    });

    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");

    const isValidEmail = (email) =>
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.trim());

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const isFormValid =
        formData.firstName.trim() !== "" &&
        formData.lastName.trim() !== "" &&
        formData.username.trim() !== "" &&
        isValidEmail(formData.email) &&
        /^\d{10}$/.test(formData.phone) &&
        formData.dob !== "" &&
        formData.gender !== "" &&
        formData.password.length >= 6 &&
        formData.password === formData.confirmPassword;

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!isFormValid) {
            if (!isValidEmail(formData.email)) {
                setErrorMsg("Invalid email format.");
            } else if (!/^\d{10}$/.test(formData.phone)) {
                setErrorMsg("Phone number must be exactly 10 digits.");
            } else if (formData.password !== formData.confirmPassword) {
                setErrorMsg("Passwords do not match.");
            } else {
                setErrorMsg("Please fill all fields.");
            }
            setSuccessMsg("");
            return;
        }

        const payload = {
            username: formData.username,
            firstName: formData.firstName,
            lastName: formData.lastName,
            phoneNumber: formData.phone,
            email: formData.email,
            password: formData.password,
            dob: formData.dob,
            gender: formData.gender,
        };

        try {
            await axios.post("/auth/register", payload);
            setSuccessMsg("Registration successful!");
            setErrorMsg("");
            setTimeout(() => navigate("/"), 1500); // ✅ redirect to login ("/")

        } catch (err) {
            const msg =
                err.response?.data || "Registration failed. Try again later.";
            setErrorMsg(msg);
            setSuccessMsg("");
            console.error("Registration error:", err);
        }
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

            {/* Right Form */}
            <div className="w-1/2 flex flex-col justify-center items-center bg-white p-10 relative z-10">
                <h2 className="text-2xl font-bold text-center mb-2">Create Your Account</h2>
                <p className="text-gray-600 italic mb-6 text-center">
                    Transform treasures into trusted loans — Sign up today!
                </p>

                <form onSubmit={handleSubmit} className="w-full max-w-md space-y-4">
                    <div className="flex gap-4">
                        <input
                            type="text"
                            name="firstName"
                            placeholder="First Name"
                            value={formData.firstName}
                            onChange={handleChange}
                            className="w-1/2 border border-gray-300 rounded px-4 py-2"
                            required
                        />
                        <input
                            type="text"
                            name="lastName"
                            placeholder="Last Name"
                            value={formData.lastName}
                            onChange={handleChange}
                            className="w-1/2 border border-gray-300 rounded px-4 py-2"
                            required
                        />
                    </div>

                    <input
                        type="text"
                        name="username"
                        placeholder="Username"
                        value={formData.username}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />

                    <input
                        type="email"
                        name="email"
                        placeholder="Email Address"
                        value={formData.email}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />

                    <input
                        type="tel"
                        name="phone"
                        placeholder="Phone Number"
                        value={formData.phone}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        maxLength={10}
                        required
                    />

                    <input
                        type="date"
                        name="dob"
                        value={formData.dob}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />

                    <select
                        name="gender"
                        value={formData.gender}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    >
                        <option value="">Select Gender</option>
                        <option value="Female">Female</option>
                        <option value="Male">Male</option>
                        <option value="Other">Other</option>
                    </select>

                    <input
                        type="password"
                        name="password"
                        placeholder="Set Password"
                        value={formData.password}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />

                    <input
                        type="password"
                        name="confirmPassword"
                        placeholder="Confirm Password"
                        value={formData.confirmPassword}
                        onChange={handleChange}
                        className="w-full border border-gray-300 rounded px-4 py-2"
                        required
                    />

                    {errorMsg && <p className="text-red-600 text-sm text-center">{errorMsg}</p>}
                    {successMsg && <p className="text-green-600 text-sm text-center">{successMsg}</p>}

                    <button
                        type="submit"
                        className={`w-full text-white py-2 rounded font-semibold ${
                            isFormValid ? "bg-[#eba75d] hover:bg-[#d88f42]" : "bg-gray-400 cursor-not-allowed"
                        }`}
                        disabled={!isFormValid}
                    >
                        SIGN UP
                    </button>
                </form>

                <p className="mt-4 text-sm">
                    Already have an account?{" "}
                    <Link to="/" className="font-semibold underline text-black">
                        Log in
                    </Link>
                </p>
            </div>
        </div>
    );
};

export default Register;
