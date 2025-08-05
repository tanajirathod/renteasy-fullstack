// src/services/interestService.js
import apiClient from './apiClient';   // relative path correct as per your folder structure


const BASE_PATH = '/interests';

const expressInterest = (interestData) => apiClient.post(BASE_PATH, interestData);
const getInterestedPropertiesByBuyer = (buyerEmail) =>
  apiClient.get(`${BASE_PATH}/buyer/${buyerEmail}`);

const getInterestedBuyersByProperty = (propertyId) =>
  apiClient.get(`${BASE_PATH}/property/${propertyId}`);

export default {
  expressInterest,
  getInterestedPropertiesByBuyer,
  getInterestedBuyersByProperty,
};
