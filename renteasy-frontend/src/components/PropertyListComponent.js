import React from 'react';

const PropertyListComponent = ({
  properties,
  user,
  onEdit,
  onDelete,
  likeCounts,
  onLike,
  onInterest,
}) => {
  const list = Array.isArray(properties) ? properties : [];

  if (list.length === 0) {
    return <p>No properties to display.</p>;
  }

  return (
    <div className="row">
      {list.map((property) => (
        <div className="col-md-4 mb-3" key={property.propertyId}>
          <div className="card h-100">
            <div className="card-body d-flex flex-column justify-content-between">
              <div>
                <h5 className="card-title">{property.title}</h5>
                <p className="card-text">
                  {property.description}<br />
                  <strong>Place:</strong> {property.place}<br />
                  <strong>Area:</strong> {property.area} sqft<br />
                  <strong>Rent:</strong> ₹{property.rentPrice}<br />
                  <strong>Bedrooms:</strong> {property.bedrooms}<br />
                  <strong>Bathrooms:</strong> {property.bathrooms}<br />
                  <strong>Hospitals Nearby:</strong> {property.hospitalsNearby || 'N/A'}<br />
                  <strong>Colleges Nearby:</strong> {property.collegesNearby || 'N/A'}<br />
                </p>
              </div>
              <div>
                {user?.role === 'SELLER' && (
                  <>
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => onEdit && onEdit(property)}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => onDelete && onDelete(property.propertyId)}
                    >
                      Delete
                    </button>
                  </>
                )}
                {user?.role === 'BUYER' && (
                  <>
                    <button
                      className="btn btn-secondary btn-sm me-2"
                      onClick={() => onLike && onLike(property.propertyId)}
                    >
                      Like {likeCounts?.[property.propertyId] || 0}
                    </button>
                    <button
                      className="btn btn-success btn-sm"
                      onClick={() => onInterest && onInterest(property.propertyId)}
                    >
                      I'm Interested
                    </button>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default PropertyListComponent;
