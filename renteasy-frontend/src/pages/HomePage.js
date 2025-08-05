import React from 'react';

function HomePage() {
  return (
    <div className="container mt-5 text-center">
      <h1 className="mb-4">
        <i className="bi bi-buildings-fill fs-1 text-primary"></i>
      </h1>
      <h2>Welcome to RentEasy</h2>
      <p className="lead mt-3">
        Simplifying your rental experience – find the perfect home or the right tenant today.
      </p>
      <img
        src="need  add image here"
        className="img-fluid rounded shadow mt-4"
        alt="RentEasy Home"
      />
    </div>
  );
}

export default HomePage;
