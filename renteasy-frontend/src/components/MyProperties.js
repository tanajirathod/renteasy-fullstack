import React, { useState, useEffect } from 'react';
import axios from 'axios';

function MyProperties() {
  const [properties, setProperties] = useState([]);
  const user = JSON.parse(localStorage.getItem('user')) || {};

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/properties/seller/${user.email}`); // Changed from EMAIL
      setProperties(res.data);
    } catch (err) {
      console.error('Error fetching properties:', err);
    }
  };

  return (
    <div className="container mt-4">
      <h2>My Properties</h2>
      {properties.length > 0 ? (
        properties.map((property) => (
          <div key={property.PROPERTY_ID} className="card mb-3">
            <div className="card-body">
              <h5>{property.TITLE}</h5>
              <p>Rent: ₹{property.RENT_PRICE}</p>
            </div>
          </div>
        ))
      ) : (
        <p>No properties found.</p>
      )}
    </div>
  );
}

export default MyProperties;
