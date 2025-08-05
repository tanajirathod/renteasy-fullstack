import React, { useState } from 'react';
import axios from 'axios';

const InterestFormComponent = ({ propertyId }) => {
  const [msg, setMsg] = useState('');
  const user = JSON.parse(localStorage.getItem('user')) || {};

  const handleSubmit = async () => {
    try {
      await axios.post('http://localhost:8080/api/interests', {
        propertyId: propertyId, // Changed from PROPERTY_ID
        buyerId: user.userId,   // Changed from BUYER_ID
      });
      setMsg('Interest marked');
    } catch (err) {
      setMsg('Failed to mark interest');
    }
  };

  return (
    <div className="mb-3">
      {msg && <div className="alert alert-info">{msg}</div>}
      <button className="btn btn-success" onClick={handleSubmit}>I'm Interested</button>
    </div>
  );
};

export default InterestFormComponent;
