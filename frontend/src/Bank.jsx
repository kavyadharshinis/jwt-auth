
import React from 'react';
import { Link } from 'react-router-dom';
import { UserIcon } from '@heroicons/react/24/solid'; // Icon for "Users"

const Bank = () => {
    return (
        <div
            className="min-h-screen bg-cover bg-center font-sans"
            style={{ backgroundImage: "url('/assets/image36.jpg')" }}
        >
            {/* Navbar */}
            <header className="bg-[#324a7d] bg-opacity-90 text-white px-8 py-4 flex justify-between items-center shadow">
                <h1 className="text-xl font-bold tracking-wide">ORNALOAN</h1>
                <nav className="flex items-center space-x-6 text-sm">
                    <Link to="/dashboard" className="hover:underline">Dashboard</Link>
                    <Link to="/transactions" className="hover:underline">Transactions</Link>
                    <Link to="/users" className="hover:underline flex items-center gap-1">
                        <UserIcon className="w-4 h-4 text-white" />
                        Users
                    </Link>
                </nav>
            </header>

            {/* Main Cards */}
            <main className="p-8 flex flex-col md:flex-row gap-6 justify-center mt-12">
                {/* View Requests Card */}
                <div className="w-64 h-80 bg-blue-900 rounded-lg shadow-md flex items-center justify-center">
                    <Link to="/viewrequests">
                        <button className="border border-gray-300 text-white px-4 py-2 rounded-md hover:bg-blue-600 font-medium">
                            View All Requests
                        </button>
                    </Link>
                </div>

                {/* Update Gold Value Card */}
                <div className="w-64 h-80 bg-blue-900 rounded-lg shadow-md flex items-center justify-center">
                    <Link to="/update-gold">
                        <button className="border border-gray-300 text-white px-4 py-2 rounded-md hover:bg-blue-600 font-medium">
                            Update Gold Value
                        </button>
                    </Link>
                </div>
            </main>
        </div>
    );
};

export default Bank;