// SellerMyProperties.js
import React, { useEffect, useState } from 'react';
import propertyService from '../services/propertyService';
import PropertyListComponent from '../components/PropertyListComponent';
import PropertyFormComponent from '../components/PropertyFormComponent';

const SellerMyProperties = () => {
  const storedUser = localStorage.getItem('user');
  const user = storedUser ? JSON.parse(storedUser) : {};
  const userId = user.userId ? Number(user.userId) : null;

  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const [editingProperty, setEditingProperty] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [message, setMessage] = useState('');

  const fetchProperties = async () => {
    if (!userId) return;
    setLoading(true);
    try {
      const response = await propertyService.getPropertiesBySeller(userId, { page, size: 6 });
      setProperties(response.data.content || []);
      setTotalPages(response.data.totalPages || 1);
    } catch {
      alert('Failed to load your properties');
      setProperties([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProperties();
  }, [userId, page]);

  const handleEdit = property => {
    setEditingProperty(property);
    setShowForm(true);
    setMessage('');
  };

  const handleDelete = async propertyId => {
    if (!window.confirm('Are you sure you want to delete this property?')) return;
    try {
      await propertyService.deleteProperty(propertyId, userId);
      setProperties(prev => prev.filter(p => p.propertyId !== propertyId));
      setMessage('Property deleted successfully!');
    } catch {
      alert('Delete failed. Please try again.');
    }
  };

  const handleAddOrUpdate = async propertyData => {
    try {
      setMessage('');
      const payload = {
        ...propertyData,
        userId,
        area: Number(propertyData.area),
        bedrooms: Number(propertyData.bedrooms),
        bathrooms: Number(propertyData.bathrooms),
        rentPrice: Number(propertyData.rentPrice),
      };

      if (editingProperty) {
        const response = await propertyService.updateProperty(editingProperty.propertyId, payload);
        setProperties(prev => prev.map(p => (p.propertyId === editingProperty.propertyId ? response.data : p)));
        setEditingProperty(null);
      } else {
        const response = await propertyService.addProperty(userId, payload);
        setProperties(prev => [...prev, response.data]);
      }
      setMessage('Property saved successfully!');
      setShowForm(false);
    } catch {
      alert('Error saving property. Please try again.');
    }
  };

  return (
    <div className="container mt-4">
      <h2>My Properties</h2>
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

      {loading ? (
        <p>Loading...</p>
      ) : properties.length === 0 ? (
        <p>You have no properties yet.</p>
      ) : (
        <>
          <PropertyListComponent
            properties={properties}
            user={user}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />
          {totalPages > 1 && (
            <div className="d-flex justify-content-between my-3">
              <button className="btn btn-secondary" onClick={() => setPage(p => Math.max(p - 1, 0))} disabled={page === 0}>
                Prev
              </button>
              <span>Page {page + 1} of {totalPages}</span>
              <button className="btn btn-secondary" onClick={() => setPage(p => Math.min(p + 1, totalPages - 1))} disabled={page + 1 >= totalPages}>
                Next
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default SellerMyProperties;
