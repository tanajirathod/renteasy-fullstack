import React, { useState } from 'react';
import PropertyFormComponent from '../components/PropertyFormComponent';
import propertyService from '../services/propertyService';

const SellerAddProperty = () => {
  // Try to extract user and userId from localStorage
  let user = {};
  let userId = null;
  try {
    user = JSON.parse(localStorage.getItem('user')) || {};
    userId = Number(user.userId);
    if (!user.userId || Number.isNaN(userId)) userId = null;
  } catch {
    userId = null;
  }

  const [isOpen, setIsOpen] = useState(true); // Form visible initially
  const [message, setMessage] = useState('');

  // The handleSubmit function will be given as a prop to PropertyFormComponent
  const handleSubmit = async (propertyData) => {
    try {
      if (!userId) {
        alert('You must be logged in as a seller!');
        return;
      }

      // Ensure all numeric values are numbers
      const payload = {
        ...propertyData,
        area: Number(propertyData.area),
        bedrooms: Number(propertyData.bedrooms),
        bathrooms: Number(propertyData.bathrooms),
        rentPrice: Number(propertyData.rentPrice),
        userId // comes from localStorage
      };

      await propertyService.addProperty(userId, payload);

      setMessage('Property added successfully!');
      setIsOpen(false); // Close the form on success
    } catch (err) {
      let msg = 'Error saving property. Please try again.';
      if (err.response?.data) {
        if (typeof err.response.data === 'string') {
          msg = err.response.data;
        } else if (err.response.data.message) {
          msg = err.response.data.message;
        }
      }
      alert(msg);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Add New Property</h2>
      {message && <div className="alert alert-success">{message}</div>}
      {isOpen ? (
        <div className="card p-3 shadow-sm border rounded mb-3">
          {/* PropertyFormComponent expects an onSubmit prop */}
          <PropertyFormComponent onSubmit={handleSubmit} />
        </div>
      ) : (
        <button
          className="btn btn-primary"
          onClick={() => { setIsOpen(true); setMessage(''); }}
        >
          Add Another Property
        </button>
      )}
    </div>
  );
};

export default SellerAddProperty;
