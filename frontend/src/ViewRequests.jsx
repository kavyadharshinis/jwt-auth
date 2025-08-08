import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "./utils/axios";
import { motion } from "framer-motion";

const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
        opacity: 1,
        transition: {
            delayChildren: 0.3,
            staggerChildren: 0.1,
        }
    }
};

const rowVariants = {
    hidden: { opacity: 0, y: 30 },
    visible: { opacity: 1, y: 0 },
};

const ViewRequests = () => {
    const [requests, setRequests] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRequests = async () => {
            const role = localStorage.getItem("role");
            if (role !== 'ROLE_EMPLOYEE') {
                setError('Unauthorized! Please login as Bank Employee.');
                return;
            }

            try {
                const res = await axios.get('/bank/deposit/pending');
                setRequests(res.data);
            } catch (err) {
                console.error('Error fetching requests:', err);
                if (err.response?.status === 401) {
                    setError('Unauthorized! Please login as Bank Employee.');
                } else {
                    setError('Something went wrong while fetching requests.');
                }
            }
        };

        fetchRequests();
    }, []);

    return (
        <motion.div
            className="min-h-screen bg-white p-10 font-sans"
            initial="hidden"
            animate="visible"
            variants={containerVariants}
        >
            <motion.h2
                className="text-xl font-bold bg-blue-600 text-white px-4 py-2 inline-block rounded"
                initial={{ opacity: 0, y: -20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.6 }}
            >
                PENDING LOAN REQUESTS
            </motion.h2>

            {error && (
                <motion.p className="text-red-600 mt-4" initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
                    ⚠ {error}
                </motion.p>
            )}

            <motion.div className="grid grid-cols-5 font-semibold text-gray-800 text-sm mt-6 mb-2 px-2">
                <div>REQUEST ID</div>
                <div>NAME</div>
                <div>JEWELER</div>
                <div>GRAMS</div>
                <div>ACTION</div>
            </motion.div>

            {requests.length > 0 ? (
                <motion.div variants={containerVariants}>
                    {requests.map((req) => (
                        <motion.div
                            key={req.id}
                            className="grid grid-cols-5 items-center bg-blue-600 text-white font-semibold rounded-lg px-4 py-3 mb-3 cursor-pointer hover:shadow-xl"
                            variants={rowVariants}
                            whileHover={{ scale: 1.02 }}
                        >
                            <div>{req.id}</div>
                            <div>{req.accountHolder || 'N/A'}</div>
                            <div>{req.jewelerName || 'Unknown'}</div>
                            <div>{req.weight || 0}g</div>
                            <div>
                                <motion.button
                                    onClick={() => navigate(`/view-requests/${req.id}`)}
                                    whileHover={{ scale: 1.05 }}
                                    className="bg-blue-900 hover:bg-blue-700 text-white px-3 py-1 rounded text-sm"
                                >
                                    INSPECT
                                </motion.button>
                            </div>
                        </motion.div>
                    ))}
                </motion.div>
            ) : (
                !error && (
                    <motion.p className="text-center mt-8 text-gray-500" initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
                        No pending requests to show.
                    </motion.p>
                )
            )}
        </motion.div>
    );
};

export default ViewRequests;
