import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';
import Footer from './components/Footer';

import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import ContactPage from './pages/ContactPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

import BuyerInterests from "./pages/BuyerInterests";
import SellerAddProperty from './pages/SellerAddProperty';
import SellerMyProperties from './pages/SellerMyProperties';
import SellerDashboard from './components/SellerDashboard';
import BuyerDashboard from './components/BuyerDashboard';
import PropertyDetails from './components/PropertyDetails';

function App() {
  return (
    <div className="d-flex flex-column min-vh-100">
      <Navbar />
      <main className="flex-fill">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/about" element={<AboutPage />} />
          <Route path="/contact" element={<ContactPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/seller-dashboard" element={<SellerDashboard />} />
          <Route path="/seller/add-property" element={<SellerAddProperty />} />
          <Route path="/my-properties" element={<SellerMyProperties />} />
          <Route path="/buyer-dashboard" element={<BuyerDashboard />} />
          <Route path="/buyer/interests" element={<BuyerInterests />} />
          <Route path="/properties/:id" element={<PropertyDetails />} />       
        </Routes>
      </main>
      <Footer />
    </div>
  );
}

export default App;
