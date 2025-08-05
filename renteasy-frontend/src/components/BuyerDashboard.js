import React, { useEffect, useState } from 'react';
import propertyService from '../services/propertyService';
import likeService from '../services/likeService';
import interestService from '../services/interestService';
import PropertyListComponent from '../components/PropertyListComponent';

const BuyerDashboard = () => {
  const [properties, setProperties] = useState([]);
  const [likeCounts, setLikeCounts] = useState({});
  const [loading, setLoading] = useState(false);
  const [filter, setFilter] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const user = JSON.parse(localStorage.getItem('user') || '{}');

  useEffect(() => {
    fetchProperties();
  }, [page]);

  const fetchProperties = async () => {
    setLoading(true);
    try {
      // Defensive: supply filter only if not empty string
      const params = {
        page,
        size: 6,
        place: filter.trim() !== '' ? filter.trim() : undefined,
      };
      // Important: Call getAllProperties (match to propertyService export)
      const res = await propertyService.getAllProperties(params);
      const data = res.data;

      setProperties(Array.isArray(data.content) ? data.content : []);
      setTotalPages(data.totalPages ?? 1);

      // Fetch like counts in parallel, can be optimized
      const counts = {};
      if (data.content && data.content.length) {
        await Promise.all(
          data.content.map(async (prop) => {
            const likeRes = await likeService.getLikeCount(prop.propertyId);
            counts[prop.propertyId] = likeRes.data;
          })
        );
      }
      setLikeCounts(counts);
    } catch (err) {
      setProperties([]);
      setLikeCounts({});
      alert('Failed to load properties.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  
  const handlePrev = () => {
    if (page > 0) setPage(page - 1);
  };
  const handleNext = () => {
    if (page + 1 < totalPages) setPage(page + 1);
  };
  const handleFilter = () => {
    setPage(0);
    fetchProperties();
  };

  const handleLike = async (id) => {
    try {
      await likeService.likeProperty({ propertyId: id, userId: user.userId });
      fetchProperties();
    } catch {
      alert('You have already liked this property.');
    }
  };

  const handleInterest = async (id) => {
    try {
      await interestService.expressInterest({ propertyId: id, buyerId: user.userId });
      alert('Marked as interested!');
    } catch {
      alert('You have already marked interest.');
    }
  };

  return (
    <div className="container mt-4">
      <h2>Browse Properties</h2>
      <div className="input-group mb-3">
        <input
          type="text"
          placeholder="Filter by place"
          className="form-control"
          value={filter}
          onChange={(e) => setFilter(e.target.value)}
        />
        <button className="btn btn-primary" onClick={handleFilter}>
          Search
        </button>
      </div>

      {loading ? (
        <p>Loading properties...</p>
      ) : (
        <>
          <PropertyListComponent
            properties={properties}
            user={user}
            likeCounts={likeCounts}
            onLike={handleLike}
            onInterest={handleInterest}
          />
          {totalPages > 1 && (
            <div className="d-flex justify-content-between mt-3">
              <button disabled={page === 0} className="btn btn-secondary" onClick={handlePrev}>
                Prev
              </button>
              <span>
                Page {page + 1} of {totalPages}
              </span>
              <button
                disabled={page + 1 === totalPages}
                className="btn btn-secondary"
                onClick={handleNext}
              >
                Next
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default BuyerDashboard;
