// src/services/propertyService.js
import apiClient from './apiClient';

const PATH = '/properties';

/**
 * Fetch paginated properties by seller.
 * If sellerId is falsy, throws error (should not fetch "my properties" with invalid ID).
 *
 * @param {number} sellerId
 * @param {object} params - { page, size, ... }
 */
export const getPropertiesBySeller = (sellerId, params = {}) => {
  if (!sellerId || Number.isNaN(Number(sellerId))) {
    throw new Error("Seller ID is required for this API call.");
  }
  return apiClient.get(`${PATH}/seller/${sellerId}`, { params });
};

/**
 * Fetch all properties (buyer dashboard, not seller owned).
 */
export const getAllProperties = (params = {}) => apiClient.get(PATH, { params });

/**
 * Get property details.
 */
export const getPropertyById = (id) => apiClient.get(`${PATH}/${id}`);

/**
 * Add property. userId in path, payload in data.
 */
export const addProperty = (userId, data) => apiClient.post(`${PATH}/add/${userId}`, data);

/**
 * Update property.
 */
export const updateProperty = (id, data) => apiClient.put(`${PATH}/${id}`, data);

/**
 * Delete property. Sends userId as param for backend check/ownership.
 */
export const deleteProperty = (id, userId) =>
  apiClient.delete(`${PATH}/${id}`, { params: { userId } });

const propertyService = {
  getPropertiesBySeller,
  getAllProperties,
  getPropertyById,
  addProperty,
  updateProperty,
  deleteProperty,
};

export default propertyService;
