import { useState, useEffect } from 'react';
import api from '../services/apiService';

function MyProperties() {
  const [properties, setProperties] = useState([]);
  const [userId] = useState(1); // Replace with logged-in user ID

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      const response = await api.get('/properties/mine', { params: { userId } });
      setProperties(response.data);
    } catch (error) {
      console.error('Error fetching properties:', error);
    }
  };

  return (
    <div>
      <h2>My Properties</h2>
      {properties.map((property) => (
        <div key={property.propertyId} className="card mb-3">
          <div className="card-body">
            <h5>{property.title}</h5>
            <p>Rent: ${property.rentPrice}</p>
          </div>
        </div>
      ))}
    </div>
  );
}

export default MyProperties;
