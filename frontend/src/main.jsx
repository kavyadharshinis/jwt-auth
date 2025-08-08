import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./index.css";

// Import your pages/components
import Login from "./Login";
import Register from "./Register";
import OAuth2Redirect from "./OAuth2Redirect.jsx";
import Dashboard from "./Dashboard.jsx";
import EmployeeDashboard from "./EmployeeDashboard.jsx";
import ChatBot from './ChatBot';
import Contact from "./Contact.jsx";
import Bank from "./Bank.jsx";
import RequestPage from "./RequestPage.jsx";
import ViewRequests from "./ViewRequests.jsx";



const MainApp = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/oauth2/redirect" element={<OAuth2Redirect />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/employee-dashboard" element={<EmployeeDashboard />} />
                <Route path="/chatbot" element={<ChatBot />} />
                <Route path="/contact" element={<Contact />} />
                <Route path="/bank" element={<Bank />} />
                <Route path="/view-requests" element={<RequestPage />} />
                <Route path="/viewrequests" element={<ViewRequests />} />




            </Routes>
        </BrowserRouter>
    );
};

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <MainApp />
    </React.StrictMode>
);
