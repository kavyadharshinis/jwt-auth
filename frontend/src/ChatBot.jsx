import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';

const ChatBot = () => {
    const [messages, setMessages] = useState([
        {
            sender: "bot",
            text: "🤖 Welcome to Star Finance Gold/Silver Loan Assistant!\n\nI can help you with:\n\n💰 Gold & Silver Rates\n💼 Loan Information\n📋 Process & Requirements\n\nWhat would you like to know?"
        }
    ]);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);
    const messagesEndRef = useRef(null);

    const suggestedQuestions = [
        "What is the current gold rate?",
        "How much loan can I get?",
        "What are the interest rates?",
        "What documents do I need?",
        "How long does the process take?",
        "What are the tenure options?"
    ];

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const handleSend = async () => {
        if (!input.trim()) return;

        const userMessage = { sender: "user", text: input };
        setMessages(prev => [...prev, userMessage]);
        setLoading(true);

        try {
            const response = await axios.post("http://localhost:8080/api/chat/ask", {
                question: input
            }, {
                withCredentials: false
            });
            const botMessage = { sender: "bot", text: response.data.answer };
            setMessages(prev => [...prev, botMessage]);
        } catch (error) {
            console.error("Chat error:", error);
            setMessages(prev => [...prev, {
                sender: "bot",
                text: "❌ Sorry, I'm having trouble connecting right now. Please try again in a moment or contact our support team."
            }]);
        }

        setInput("");
        setLoading(false);
    };

    const handleSuggestedQuestion = (question) => {
        setInput(question);
    };

    const formatMessage = (text) => {
        return text.split('\n').map((line, index) => (
            <div key={index} style={{ marginBottom: line.trim() ? '8px' : '4px' }}>
                {line}
            </div>
        ));
    };

    return (
        <div className="max-w-4xl mx-auto bg-white rounded-lg shadow-lg overflow-hidden">
            {/* Header */}
            <div className="bg-gradient-to-r from-yellow-500 to-yellow-600 text-white p-6">
                <div className="flex items-center space-x-3">
                    <div className="w-12 h-12 bg-white bg-opacity-20 rounded-full flex items-center justify-center">
                        <span className="text-2xl">🏦</span>
                    </div>
                    <div>
                        <h2 className="text-xl font-bold">Star Finance Assistant</h2>
                        <p className="text-yellow-100 text-sm">Gold & Silver Loan Expert</p>
                    </div>
                </div>
            </div>

            {/* Chat Messages */}
            <div className="h-96 overflow-y-auto p-4 bg-gray-50">
                {messages.map((msg, idx) => (
                    <div key={idx} className={`mb-4 ${msg.sender === "user" ? "text-right" : "text-left"}`}>

                    <div className={`inline-block max-w-xs lg:max-w-md px-4 py-2 rounded-lg ${
                            msg.sender === "user"
                                ? "bg-blue-500 text-white"
                                : "bg-white text-gray-800 shadow-sm border"
                        }`}>
                            <div className="whitespace-pre-line">
                                {formatMessage(msg.text)}
                            </div>
                        </div>
                    </div>
                ))}

                {loading && (
                    <div className="text-left mb-4">
                        <div className="inline-block bg-white text-gray-800 shadow-sm border px-4 py-2 rounded-lg">
                            <div className="flex items-center space-x-2">
                                <div className="flex space-x-1">
                                    <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                                    <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{animationDelay: '0.1s'}}></div>
                                    <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{animationDelay: '0.2s'}}></div>
                                </div>
                                <span className="text-sm text-gray-500">Assistant is typing...</span>
                            </div>
                        </div>
                    </div>
                )}
                <div ref={messagesEndRef} />
            </div>

            {/* Suggested Questions */}
            {messages.length === 1 && (
                <div className="p-4 bg-gray-50 border-t">
                    <p className="text-sm text-gray-600 mb-3">💡 Try asking:</p>
                    <div className="flex flex-wrap gap-2">
                        {suggestedQuestions.map((question, index) => (
                            <button
                                key={index}
                                onClick={() => handleSuggestedQuestion(question)}
                                className="px-3 py-1 bg-white border border-gray-300 rounded-full text-sm text-gray-700 hover:bg-gray-50 hover:border-gray-400 transition-colors"
                            >
                                {question}
                            </button>
                        ))}
                    </div>
                </div>
            )}

            {/* Input Area */}
            <div className="p-4 bg-white border-t">
                <div className="flex space-x-2">
                    <input
                        type="text"
                        value={input}
                        onChange={e => setInput(e.target.value)}
                        onKeyDown={e => e.key === "Enter" && handleSend()}
                        placeholder="Ask about gold rates, loan amounts, interest rates..."
                        className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:border-transparent"
                        disabled={loading}
                    />
                    <button
                        onClick={handleSend}
                        disabled={loading || !input.trim()}
                        className="px-6 py-2 bg-yellow-500 text-white rounded-lg hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                    >
                        {loading ? (
                            <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                        ) : (
                            "Send"
                        )}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ChatBot;