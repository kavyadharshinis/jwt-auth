// src/EmployeeDashboard.jsx
import React from "react";

const EmployeeDashboard = () => {
    const username = localStorage.getItem("username");
    console.log("EmployeeDashboard loaded");

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>Welcome, {username}!</h1>
            <p>This is your bank employee dashboard.</p>
        </div>
    );
};

export default EmployeeDashboard;
