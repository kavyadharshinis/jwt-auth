// src/pages/RequestPage.jsx
import React from 'react';
import RequestCard from './components/RequestCard';

const RequestPage = () => {
    const dummyRequests = [
        {
            name: "John Doe",
            email: "kavyadharshini164@gmail.com",
            type: "Gold Loan",
            amount: 100000
        },
        {
            name: "Jane Smith",
            email: "sec21cj027@gmail.com",
            type: "Silver Loan",
            amount: 50000
        }
    ];

    return (
        <div className="min-h-screen bg-gray-100 p-6 flex flex-col items-center">
            <h2 className="text-2xl font-bold mb-6">Client Loan Requests</h2>
            {dummyRequests.map((request, idx) => (
                <RequestCard key={idx} request={request} />
            ))}
        </div>
    );
};

export default RequestPage;
