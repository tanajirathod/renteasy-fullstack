// src/services/likeService.js
import apiClient from './apiClient';

const BASE_PATH = '/likes';

const likeProperty = (likeData) => apiClient.post(BASE_PATH, likeData);
const getLikeCount = (propertyId) => apiClient.get(`${BASE_PATH}/${propertyId}/count`);

const likeService = {
  likeProperty,
  getLikeCount,
};

export default likeService;
