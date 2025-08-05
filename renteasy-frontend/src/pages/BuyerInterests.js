// src/pages/BuyerInterests.js
import { useEffect, useState } from "react";
//import interestService from "../services/interetsService";
import interestService from "../services/interestService";

import PropertyListComponent from "../components/PropertyListComponent";

const BuyerInterests = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const buyerEmail = user?.email;

  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!buyerEmail) return;

    const fetchInterestProperties = async () => {
      setLoading(true);
      try {
        const res = await interestService.getInterestedPropertiesByBuyer(buyerEmail);
        setProperties(res.data || []);
      } catch (err) {
        console.error("Error fetching interested properties:", err);
        alert("Failed to load your interested properties.");
      }
      setLoading(false);
    };

    fetchInterestProperties();
  }, [buyerEmail]);

  return (
    <div className="container mt-4">
      <h2>Your Interested Properties</h2>
      {loading && <p>Loading interested properties...</p>}
      {!loading && properties.length === 0 && (
        <p>You have not marked any properties as interested yet.</p>
      )}
      {!loading && properties.length > 0 && (
        <PropertyListComponent properties={properties} />
      )}
    </div>
  );
};

export default BuyerInterests;
