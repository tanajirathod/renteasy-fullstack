// src/pages/RegisterPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../services/apiClient';

const RegisterPage = () => {
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    role: 'BUYER',
  });
  const [msg, setMsg] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      const res = await apiClient.post('/users/register', form);
      if (res.status === 200 || res.status === 201) {
        setMsg('Registration successful');
        setTimeout(() => navigate('/login'), 2000);
      } else {
        setMsg(res.data.message || 'Registration failed due to an unknown error');
      }
    } catch (err) {
      setMsg(err.response?.data?.message || 'Registration failed due to a server error');
    }
  };

  return (
    <div className="container mt-5 col-md-6">
      <h2 className="mb-4 text-primary"><i className="bi bi-person-plus-fill"></i> Register</h2>
      {msg && (
        <div className={`alert ${msg.includes('successful') ? 'alert-success' : 'alert-danger'}`}>{msg}</div>
      )}
      <form onSubmit={handleSubmit}>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label>First Name:</label>
            <input name="firstName" className="form-control" value={form.firstName} onChange={handleChange} required />
          </div>
          <div className="form-group col-md-6 mb-3">
            <label>Last Name:</label>
            <input name="lastName" className="form-control" value={form.lastName} onChange={handleChange} required />
          </div>
        </div>

        <div className="form-group mb-3">
          <label>Email:</label>
          <input type="email" name="email" className="form-control" value={form.email} onChange={handleChange} required />
        </div>

        <div className="form-group mb-3">
          <label>Phone:</label>
          <input type="text" name="phone" className="form-control" value={form.phone} onChange={handleChange} required
            placeholder="Enter 10-digit phone number" pattern="\d{10}" title="Phone number must be exactly 10 digits" />
        </div>

        <div className="form-group mb-3">
          <label>Password:</label>
          <input type="password" name="password" className="form-control" value={form.password} onChange={handleChange} required />
        </div>

        <div className="form-group mb-4">
          <label>Role:</label>
          <select name="role" className="form-control" value={form.role} onChange={handleChange}>
            <option value="BUYER">Buyer</option>
            <option value="SELLER">Seller</option>
          </select>
        </div>

        <button type="submit" className="btn btn-primary">
          <i className="bi bi-person-plus-fill"></i> Register
        </button>
      </form>
    </div>
  );
};

export default RegisterPage;
