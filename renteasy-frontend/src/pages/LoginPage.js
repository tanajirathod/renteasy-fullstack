// src/pages/LoginPage.js
import React, { useState } from 'react';
import apiClient from '../services/apiClient';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const res = await apiClient.post('/users/login', { email, password });
      const { token, user } = res.data;
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      if (user.role === 'SELLER') {
        navigate('/seller-dashboard');
      } else {
        navigate('/buyer-dashboard');
      }
    } catch (err) {
      setError('Invalid email or password');
    }
  };

  return (
    <form onSubmit={handleLogin} className="container mt-5 col-md-6">
      <h2 className="mb-4">Login</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <input
        type="email"
        placeholder="Email"
        className="form-control mb-3"
        required
        value={email}
        onChange={e => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        className="form-control mb-3"
        required
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <button type="submit" className="btn btn-primary">Login</button>
    </form>
  );
};

export default LoginPage;
