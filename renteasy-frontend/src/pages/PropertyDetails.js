import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import InterestFormComponent from './InterestFormComponent';

function PropertyDetails() {
  const { id } = useParams();
  const [property, setProperty] = useState(null);

  useEffect(() => {
    fetchProperty();
  }, [id]);

  const fetchProperty = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/properties/${id}`);
      setProperty(res.data);
    } catch (err) {
      console.error('Error fetching property:', err);
    }
  };

  if (!property) return <div>Loading...</div>;

  return (
    <div className="container mt-4">
      <h2>{property.title}</h2> {/* Changed from TITLE */}
      <p>Place: {property.place}</p> {/* Changed from PLACE */}
      <p>Area: {property.area} sq ft</p> {/* Changed from AREA */}
      <p>Rent: ₹{property.rentPrice}</p> {/* Changed from RENT_PRICE */}
      <InterestFormComponent propertyId={property.propertyId} /> {/* Changed from PROPERTY_ID */}
    </div>
  );
}

export default PropertyDetails;
