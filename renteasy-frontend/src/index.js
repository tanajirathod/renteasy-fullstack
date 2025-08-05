import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './index.css';

// Clear login session on fresh start in local dev
if (window.location.hostname === "localhost") {
  if (!window.sessionStorage.getItem("freshLoad")) {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    window.sessionStorage.setItem("freshLoad", "1");
  }
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
