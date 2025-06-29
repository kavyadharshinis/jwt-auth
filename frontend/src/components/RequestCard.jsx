// src/components/RequestCard.jsx
import React from "react";
import axios from "../utils/axios";

const RequestCard = ({ request }) => {
    const handleAccept = async () => {
        try {
            const res = await axios.post(
                "/bank/accept-request",
                null,
                {
                    params: {
                        email: request.email,
                        name: request.name,
                    }
                }
            );
            alert("✅ " + res.data);
        } catch (err) {
            console.error("❌ Email send error:", err.response?.data || err.message);
            alert("Failed to send email. See console.");
        }
    };

    return (
        <div className="bg-white p-6 rounded shadow-md mb-4 w-full max-w-md">
            <h3 className="text-lg font-semibold">{request.name}</h3>
            <p><strong>Email:</strong> {request.email}</p>
            <p><strong>Type:</strong> {request.type}</p>
            <p><strong>Amount:</strong> ₹{request.amount.toLocaleString()}</p>
            <button
                onClick={handleAccept}
                className="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded"
            >
                Accept Request
            </button>
        </div>
    );
};

export default RequestCard;
