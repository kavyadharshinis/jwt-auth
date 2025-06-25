import React, { useState } from 'react';
import axios from 'axios';

const Contact = () => {
    const [form, setForm] = useState({
        firstName: '',
        lastName: '',
        email: '',
        message: '',
        policy: false,
    });
    const [status, setStatus] = useState('');

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setForm((prev) => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!form.policy) {
            setStatus('You must agree to the privacy policy.');
            return;
        }
        try {
            await axios.post('http://localhost:8080/api/contact/send', {
                firstName: form.firstName,
                lastName: form.lastName,
                email: form.email,
                message: form.message,
            });
            setStatus('Message sent successfully!');
            setForm({
                firstName: '',
                lastName: '',
                email: '',
                message: '',
                policy: false,
            });
        } catch (err) {
            setStatus('Failed to send message. Please try again later.');
        }
    };

    return (
        <div style={style} className="in-h-screen bg-[#F1DBC3] flex items-center justify-center px-4 py-12 sm:px-6 lg:px-8">
            <div className="max-w-2xl w-full space-y-8 bg-white bg-opacity-80 p-10 rounded-xl shadow-md backdrop-blur-sm">
                <div>
                    <h2 className="text-center text-3xl font-extrabold text-gray-900">REACH OUT</h2>
                    <p className="mt-2 text-center text-sm text-gray-500">
                        Let us know how we can help — we'd love to hear from you!
                    </p>
                </div>
                <form className="space-y-6" onSubmit={handleSubmit}>
                    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
                        <div>
                            <label className="block text-sm font-medium text-gray-700">First name</label>
                            <input
                                type="text"
                                name="firstName"
                                value={form.firstName}
                                onChange={handleChange}
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700">Last name</label>
                            <input
                                type="text"
                                name="lastName"
                                value={form.lastName}
                                onChange={handleChange}
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                            />
                        </div>
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700">Email</label>
                        <input
                            type="email"
                            name="email"
                            value={form.email}
                            onChange={handleChange}
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700">Message</label>
                        <textarea
                            rows="4"
                            name="message"
                            value={form.message}
                            onChange={handleChange}
                            className="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                        ></textarea>
                    </div>

                    <div className="flex items-center">
                        <input
                            type="checkbox"
                            name="policy"
                            checked={form.policy}
                            onChange={handleChange}
                            className="h-4 w-4 text-indigo-600 border-gray-300 rounded"
                        />
                        <label className="ml-2 block text-sm text-gray-900">
                            By selecting this, you agree to our{' '}
                            <a href="#" className="text-indigo-600 hover:underline">
                                privacy policy
                            </a>.
                        </label>
                    </div>

                    <div>
                        <button
                            style={buttonStyle}
                            type="submit"
                            className="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
                        >
                            Let's talk
                        </button>
                    </div>
                    {status && <div className="text-center text-sm mt-2">{status}</div>}
                </form>
            </div>
        </div>
    );
};

const style = {
    backgroundColor: '#fef6e4',
};

const buttonStyle = {
    backgroundColor: '#EBA75D',
    color: 'white',
    padding: '0.5rem 1rem',
    border: 'none',
    borderRadius: '0.375rem',
    fontSize: '0.875rem',
    fontWeight: '500',
    width: '100%',
    cursor: 'pointer',
};

export default Contact;