import React from "react";
import ChatBot from "./ChatBot";

const Dashboard = () => {
    return (
        <div className="min-h-screen bg-gradient-to-br from-yellow-50 to-orange-50 p-6">
            <div className="max-w-7xl mx-auto">
                {/* Header */}
                <div className="text-center mb-8">
                    <h1 className="text-4xl font-bold text-gray-800 mb-4">
                        🏦 Star Finance Dashboard
                    </h1>
                    <p className="text-lg text-gray-600 max-w-2xl mx-auto">
                        Your trusted partner for gold and silver loans. Get instant answers about rates,
                        loan amounts, and application processes through our AI assistant.
                    </p>
                </div>

                {/* Features Grid */}
                <div className="grid md:grid-cols-3 gap-6 mb-8">
                    <div className="bg-white rounded-lg shadow-md p-6 text-center">
                        <div className="text-3xl mb-3">💰</div>
                        <h3 className="text-xl font-semibold mb-2">Live Gold Rates</h3>
                        <p className="text-gray-600">Get real-time gold and silver market prices</p>
                    </div>
                    <div className="bg-white rounded-lg shadow-md p-6 text-center">
                        <div className="text-3xl mb-3">📊</div>
                        <h3 className="text-xl font-semibold mb-2">Loan Calculator</h3>
                        <p className="text-gray-600">Calculate potential loan amounts instantly</p>
                    </div>
                    <div className="bg-white rounded-lg shadow-md p-6 text-center">
                        <div className="text-3xl mb-3">🤖</div>
                        <h3 className="text-xl font-semibold mb-2">AI Assistant</h3>
                        <p className="text-gray-600">24/7 support for all your loan queries</p>
                    </div>
                </div>

                {/* ChatBot Section */}
                <div className="bg-white rounded-lg shadow-lg p-6">
                    <div className="text-center mb-6">
                        <h2 className="text-2xl font-bold text-gray-800 mb-2">
                            💬 Ask Our AI Assistant
                        </h2>
                        <p className="text-gray-600">
                            Get instant answers about gold rates, loan amounts, interest rates, and more!
                        </p>
                    </div>
                    <ChatBot />
                </div>

                {/* Quick Info */}
                <div className="mt-8 grid md:grid-cols-2 gap-6">
                    <div className="bg-white rounded-lg shadow-md p-6">
                        <h3 className="text-xl font-semibold mb-4 text-gray-800">Why Choose Star Finance?</h3>
                        <ul className="space-y-2 text-gray-600">
                            <li>✅ Competitive interest rates starting from 7.5%</li>
                            <li>✅ Quick approval within 2-4 hours</li>
                            <li>✅ No credit score required</li>
                            <li>✅ Secure vault storage</li>
                            <li>✅ Flexible repayment options</li>
                            <li>✅ 24/7 customer support</li>
                        </ul>
                    </div>
                    <div className="bg-white rounded-lg shadow-md p-6">
                        <h3 className="text-xl font-semibold mb-4 text-gray-800">Quick Facts</h3>
                        <ul className="space-y-2 text-gray-600">
                            <li>💰 Gold Loan LTV: 70-80% of market value</li>
                            <li>🥈 Silver Loan LTV: 60-70% of market value</li>
                            <li>⏰ Tenure: 3 months to 36 months</li>
                            <li>📋 Minimum: 10g gold or 100g silver</li>
                            <li>💳 Processing Fee: 1-2% of loan amount</li>
                            <li>🔒 Security: Bank-grade vault storage</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;