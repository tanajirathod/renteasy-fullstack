import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
  const navigate = useNavigate();
  let user = {};
  const userString = localStorage.getItem('user');
  if (userString) {
    try {
      user = JSON.parse(userString);
    } catch (e) {
      console.error('Invalid JSON in localStorage:', e);
      localStorage.removeItem('user');
    }
  }

  const handleLogout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    // Clear session storage if used
    sessionStorage.clear();
    window.location.href = '/';  // Redirect after logout
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <Link className="navbar-brand " to="/">RentEasy</Link>
        <div className="d-flex align-items-center ms-auto">
          {user.userId && (
            <span className="text-white me-4">
              Welcome, {user.firstName} 
            </span>
          )}

          {!user.userId ? (
            <>
              <Link className="nav-link text-white me-3" to="/login">Login</Link>
              <Link className="nav-link text-white me-3" to="/register">Register</Link>
            </>
          ) : (
            <>
              {user.role === 'SELLER' && (
                <>
                  <Link className="nav-link text-white me-3" to="/my-properties">My Properties</Link>
                  <Link className="nav-link text-white me-3" to="/seller/add-property">Add Property</Link>
                  {/* Dashboard button removed as per requirement */}
                </>
              )}

              {user.role === 'BUYER' && (
                <>
                  <Link className="nav-link text-white me-3" to="/buyer-dashboard">Browse Properties</Link>
                  <Link className="nav-link text-white me-3" to="/buyer/interests">My Interests</Link>
                </>
              )}

              <button className="btn btn-outline-light ms-2" onClick={handleLogout}>Logout</button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
