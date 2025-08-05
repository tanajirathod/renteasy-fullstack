import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import InterestFormComponent from './InterestFormComponent';

function PropertyDetails() {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [likeCount, setLikeCount] = useState(0);

  useEffect(() => {
    fetchProperty();
    fetchLikeCount();
  }, [id]);

  const fetchProperty = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/properties/${id}`);
      setProperty(res.data);
    } catch (err) {
      console.error('Error fetching property:', err);
    }
  };

  const fetchLikeCount = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/likes/${id}/count`);
      setLikeCount(res.data || 0);
    } catch (err) {
      console.error('Error fetching like count:', err);
    }
  };

  const likeProperty = async () => {
    try {
      const user = JSON.parse(localStorage.getItem('user')) || {};
      await axios.post('http://localhost:8080/api/likes', { propertyId: id, userId: user.userId });
      fetchLikeCount();
    } catch (err) {
      console.error('Error liking property:', err);
    }
  };

  if (!property) return <div>Loading...</div>;

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-12 col-md-8">
          <h2 className="mb-3">{property.title}</h2>
          <p className="mb-2"><strong>Place:</strong> {property.place}</p>
          <p className="mb-2"><strong>Area:</strong> {property.area} sq ft</p>
          <p className="mb-2"><strong>Rent:</strong> ₹{property.rentPrice}</p>
          <p className="mb-2"><strong>Description:</strong> {property.description}</p>
        </div>
        <div className="col-12 col-md-4">
          <InterestFormComponent propertyId={property.propertyId} />
          <button onClick={likeProperty} className="btn btn-secondary w-100 mt-2">
            Like ({likeCount})
          </button>
        </div>
      </div>
    </div>
  );
}

export default PropertyDetails;
