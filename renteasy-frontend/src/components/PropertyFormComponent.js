// PropertyFormComponent.js
import React, { useState, useEffect } from 'react';

const PropertyFormComponent = ({ onSubmit, editingProperty }) => {
  const [form, setForm] = useState({
    title: '',
    description: '',
    place: '',
    area: '',
    bedrooms: 0,
    bathrooms: 0,
    hospitalsNearby: '',
    collegesNearby: '',
    rentPrice: 0,
  });

  useEffect(() => {
    if (editingProperty) {
      setForm({
        title: editingProperty.title || '',
        description: editingProperty.description || '',
        place: editingProperty.place || '',
        area: editingProperty.area || '',
        bedrooms: editingProperty.bedrooms || 0,
        bathrooms: editingProperty.bathrooms || 0,
        hospitalsNearby: editingProperty.hospitalsNearby || '',
        collegesNearby: editingProperty.collegesNearby || '',
        rentPrice: editingProperty.rentPrice || 0,
      });
    } else {
      setForm({
        title: '',
        description: '',
        place: '',
        area: '',
        bedrooms: 0,
        bathrooms: 0,
        hospitalsNearby: '',
        collegesNearby: '',
        rentPrice: 0,
      });
    }
  }, [editingProperty]);

  const handleChange = e => {
    const { name, value } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: ['area', 'bedrooms', 'bathrooms', 'rentPrice'].includes(name) && value !== '' ? Number(value) : value
    }));
  };

  const handleSubmit = e => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="mb-4 border rounded p-3 shadow-sm bg-light">
      {['title', 'description', 'place', 'hospitalsNearby', 'collegesNearby'].map(field => (
        <div className="mb-3" key={field}>
          <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}</label>
          {field === 'description' ? (
            <textarea
              name={field}
              className="form-control"
              value={form[field] || ''}
              onChange={handleChange}
              rows="4"
              maxLength={500}
            />
          ) : (
            <input
              type="text"
              name={field}
              className="form-control"
              value={form[field] || ''}
              onChange={handleChange}
              required={field !== 'hospitalsNearby' && field !== 'collegesNearby'}
            />
          )}
        </div>
      ))}
      <div className="row">
        {['area', 'bedrooms', 'bathrooms', 'rentPrice'].map(field => (
          <div className="mb-3 col-md-3" key={field}>
            <label>{field.charAt(0).toUpperCase() + field.slice(1)}</label>
            <input
              type="number"
              name={field}
              className="form-control"
              value={form[field] === 0 ? 0 : form[field] || ''}
              onChange={handleChange}
              min={field === 'rentPrice' || field === 'area' ? 1 : 0}
              required
            />
          </div>
        ))}
      </div>
      <button type="submit" className="btn btn-primary">{editingProperty ? 'Update Property' : 'Add Property'}</button>
    </form>
  );
};

export default PropertyFormComponent;
