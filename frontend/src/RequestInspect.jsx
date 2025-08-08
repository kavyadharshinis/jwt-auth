import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';


const RequestInspect = () => {
    const { id } = useParams();
    const [request, setRequest] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`/api/requests/${id}`)
            .then(res => setRequest(res.data))
            .catch(err => console.error(err));
    }, [id]);

    const handleVerify = async () => {
        try {
            await axios.post(`/api/requests/${id}/verify`);
            alert("Request verified & notification sent!");
            navigate("/view-requests");
        } catch (err) {
            console.error("Verification failed", err);
        }
    };


    if (!request) {
        return (
            <motion.div
                className="p-10 text-lg text-gray-700"
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
            >
                Loading...
            </motion.div>
        );
    }

    return (
        <motion.div
            className="min-h-screen p-10 bg-white"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.6 }}
        >
            <motion.h2
                className="text-2xl font-bold text-blue-900 mb-6"
                initial={{ y: -20, opacity: 0 }}
                animate={{ y: 0, opacity: 1 }}
                transition={{ duration: 0.5 }}
            >
                Request Details
            </motion.h2>

            <motion.div
                className="space-y-4"
                initial="hidden"
                animate="visible"
                variants={{
                    hidden: {},
                    visible: {
                        transition: {
                            staggerChildren: 0.15,
                        }
                    }
                }}
            >
                {/* Detail Fields */}
                {[
                    { label: "Name", value: request.name },
                    { label: "Metal Type", value: request.metalType },
                    { label: "Grams", value: request.grams },
                    { label: "Bank Name", value: request.bankName },
                    { label: "Bank Branch", value: request.bankBranch }
                ].map((item, i) => (
                    <motion.div
                        key={i}
                        className="text-gray-800 text-base"
                        variants={{ hidden: { opacity: 0, y: 10 }, visible: { opacity: 1, y: 0 } }}
                    >
                        <strong>{item.label}:</strong> {item.value}
                    </motion.div>
                ))}

                {/* Image */}
                <motion.div
                    variants={{ hidden: { opacity: 0 }, visible: { opacity: 1 } }}
                    className="mt-4"
                >
                    <div><strong>Ornament Photo:</strong></div>
                    <motion.img
                        src={request.ornamentPhotoUrl}
                        alt="Ornament"
                        className="w-64 h-64 object-contain border rounded shadow"
                        initial={{ opacity: 0, scale: 0.9 }}
                        animate={{ opacity: 1, scale: 1 }}
                        transition={{ duration: 0.5 }}
                    />
                </motion.div>

                {/* Verify Button */}
                <motion.button
                    onClick={handleVerify}
                    className="mt-6 bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded shadow"
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                >
                    Verify & Notify
                </motion.button>
            </motion.div>
        </motion.div>
    );
};

export default RequestInspect;