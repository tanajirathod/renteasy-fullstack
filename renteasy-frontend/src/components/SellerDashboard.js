import React, { useEffect, useState } from 'react';
import propertyService from '../services/propertyService';
import PropertyFormComponent from '../components/PropertyFormComponent';
import PropertyListComponent from '../components/PropertyListComponent';

const SellerDashboard = () => {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const userId = user.userId;

  const [properties, setProperties] = useState([]);
  const [editingProperty, setEditingProperty] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [message, setMessage] = useState('');

  useEffect(() => {
    if (userId) fetchSellerProperties();
  }, [userId]);

  const fetchSellerProperties = async () => {
    try {
      const res = await propertyService.getPropertiesBySeller(userId);
      setProperties(Array.isArray(res.data) ? res.data : []);
    } catch {
      alert('Error fetching your properties');
    }
  };

  const handleAddOrUpdate = async (propertyData) => {
    try {
      if (!userId) {
        alert('Please login again');
        return;
      }
      setMessage('');
      const payload = { ...propertyData, userId };

      if (editingProperty) {
        const res = await propertyService.update(propertyData.propertyId || editingProperty.propertyId, payload);
        setProperties(prev => prev.map(p => (p.propertyId === editingProperty.propertyId ? res.data : p)));
        setEditingProperty(null);
      } else {
        const res = await propertyService.add(userId, payload);
        setProperties(prev => [...prev, res.data]);
      }
      setMessage('Property saved successfully!');
      setShowForm(false);
    } catch {
      alert('Error saving property');
    }
  };

  const handleEdit = property => {
    setEditingProperty(property);
    setShowForm(true);
  };

  const handleDelete = async (propertyId) => {
    if (!window.confirm('Do you want to delete this property?')) return;
    try {
      await propertyService.remove(propertyId, userId);
      setProperties(prev => prev.filter(p => p.propertyId !== propertyId));
      alert('Property deleted successfully');
    } catch {
      alert('Delete failed');
    }
  };

  return (
    <div className="container mt-4">
      <h2>Seller Dashboard</h2>

      <button
        className="btn btn-outline-primary my-3"
        onClick={() => {
          setShowForm(!showForm);
          setEditingProperty(null);
          setMessage('');
        }}
      >
        {showForm ? 'Cancel' : 'Add Property'}
      </button>

      {message && <div className="alert alert-success">{message}</div>}

      {showForm && (
        <PropertyFormComponent onSubmit={handleAddOrUpdate} editingProperty={editingProperty} />
      )}

      <h4>Your Properties</h4>
      <PropertyListComponent
        properties={properties}
        user={user}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    </div>
  );
};

export default SellerDashboard;
